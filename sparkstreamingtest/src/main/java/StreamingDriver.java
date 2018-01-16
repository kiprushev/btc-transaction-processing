import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.rowset.internal.Row;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.SQLContext;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.streaming.Duration;
import org.apache.spark.streaming.api.java.JavaDStream;
import org.apache.spark.streaming.api.java.JavaInputDStream;
import org.apache.spark.streaming.api.java.JavaStreamingContext;
import org.apache.spark.streaming.kafka010.ConsumerStrategies;
import org.apache.spark.streaming.kafka010.KafkaUtils;
import org.apache.spark.streaming.kafka010.LocationStrategies;
import org.elasticsearch.spark.rdd.api.java.JavaEsSpark;
import org.elasticsearch.spark.streaming.api.java.JavaEsSparkStreaming;

import java.util.*;


/**
 * Created by Kyrylo_Kiprushev on 12/22/2017.
 */
public class StreamingDriver {
    private static final Collection<String> TOPICS = Arrays.asList("crypto");

    // Misc Kafka client properties
    private static Map<String, Object> getKafkaParams() {
        Map<String, Object> kafkaParams = new HashMap<>();
        kafkaParams.put("bootstrap.servers", "kafka:9092");
        kafkaParams.put("key.deserializer", StringDeserializer.class);
        kafkaParams.put("value.deserializer", StringDeserializer.class);
        kafkaParams.put("group.id", "DEFAULT_GROUP_ID");
        kafkaParams.put("auto.offset.reset", "latest");
        kafkaParams.put("enable.auto.commit", false);
        return kafkaParams;
    }

    public static void main(String[] args) throws Exception {

        // Create a local StreamingContext with two working thread and batch interval of 1 second


        SparkConf conf = new SparkConf().setAppName("Btc transactions to Elastic").setMaster("local[2]");
        conf.set("es.index.auto.create", "true");
        conf.set("es.nodes", "localhost");
        conf.set("es.port","9200");

//        SparkSession sparkSession = SparkSession.builder()
//                .config(conf)
//                .enableHiveSupport()
//                .getOrCreate();

        try (JavaStreamingContext ssc = new JavaStreamingContext(conf, new Duration(60000))) {

//            JavaInputDStream<ConsumerRecord<String, String>> stream =
//                    KafkaUtils.createDirectStream(
//                            ssc,
//                            LocationStrategies.PreferConsistent(),
//                            ConsumerStrategies.<String, String>Subscribe(TOPICS, getKafkaParams())
//                    );
         JavaInputDStream<String> stream = ssc.socketTextStream("localhost", 4444);

            //SQLContext sqlContext = new SQLContext(sparkSession);

            System.out.println(stream.map(v-> new ObjectMapper().readValue(v, CryptoData.class)).count());

            stream.foreachRDD(rdd->{
                List<CryptoData> list = new ArrayList<>();
                Long count = rdd.map(v-> new ObjectMapper().readValue(v, CryptoData.class)).count();
                   list =  rdd.map(v-> new ObjectMapper().readValue(v, CryptoData.class))
                            .filter(cryptoData -> cryptoData.getBasecurrency().equals("USD") && cryptoData.getCryptocurrency().equals("BTC")).collect();


                if (list.size()>0){
                    Double avg = list.stream().mapToDouble(v->Double.parseDouble(v.getPrice())).average().getAsDouble();
                    System.out.println(avg);
                    System.out.println(count);
                }

//

            });
//                .foreachRDD(cryptoDataJavaRDD -> cryptoDataJavaRDD.collect().forEach(v-> System.out.println(v)));

                //.reduce((a,b) -> Integer.parseInt(a.getPrice())+Integer.parseInt(b.getPrice()));

          JavaEsSparkStreaming.saveJsonToEs(stream,"something/transactions");


            ssc.start();
            ssc.awaitTermination();

        }

    }
}
