import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.streaming.Duration;
import org.apache.spark.streaming.api.java.JavaInputDStream;
import org.apache.spark.streaming.api.java.JavaStreamingContext;
import org.apache.spark.streaming.kafka010.ConsumerStrategies;
import org.apache.spark.streaming.kafka010.KafkaUtils;
import org.apache.spark.streaming.kafka010.LocationStrategies;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;


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
    public static void printRDD(final JavaPairRDD<String,String> s) {
        s.foreach( v -> System.out.println(v._2()));
    }
    public static void main(String[] args) throws Exception {

        // Create a local StreamingContext with two working thread and batch interval of 1 second

        SparkConf conf = new SparkConf().setAppName("Btc transactions to Elastic").setMaster("local[2]");
        conf.set("es.index.auto.create", "true");

        try (JavaStreamingContext ssc = new JavaStreamingContext(conf, new Duration(1000))) {

            JavaInputDStream<ConsumerRecord<String, String>> stream = //ssc.socketTextStream("localhost", 4444);
                    KafkaUtils.createDirectStream(
                            ssc,
                            LocationStrategies.PreferConsistent(),
                            ConsumerStrategies.<String, String>Subscribe(TOPICS, getKafkaParams())
                    );
           // JavaInputDStream< String> stream = ssc.socketTextStream("localhost", 4444);

            stream.foreachRDD(r -> {
                System.out.println("*** got an RDD, size = " + r.count());
                    r.foreach(s -> System.out.println(s));
            if (r.count() > 0) {
                // let's see how many partitions the resulting RDD has -- notice that it has nothing
                // to do with the number of partitions in the RDD used to publish the data (4), nor
                // the number of partitions of the topic (which also happens to be four.)
                System.out.println("*** " + r.getNumPartitions() + " partitions");
                r.glom().foreach(a -> System.out.println("*** partition size = " + a.size()));
            }
    });

                    //foreachRDD(rdd-> rdd.foreach(v ->v.value()));
            ssc.start();
            ssc.awaitTermination();

        }

    }
}
