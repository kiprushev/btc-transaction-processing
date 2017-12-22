package generator;

import com.satori.rtm.*;
import com.satori.rtm.model.*;
import org.apache.kafka.clients.producer.ProducerRecord;
import sender.KafkaSender;


/**
 * Created by Kyrylo_Kiprushev on 12/21/2017.
 */
public class BTCGenerator {

    KafkaSender sender = new KafkaSender();
    static final String endpoint = "wss://open-data.api.satori.com";
    static final String appkey = "B6dFab9fAb0D392dE6CdeeEcEc0FCB1a";
    static final String channel = "cryptocurrency-market-data";

    public void generate() {
        final RtmClient client = new RtmClientBuilder(endpoint, appkey)
                .setListener(new RtmClientAdapter() {
                    @Override
                    public void onEnterConnected(RtmClient client) {
                        System.out.println("Connected to Satori RTM!");
                    }
                })
                .build();

        SubscriptionAdapter listener = new SubscriptionAdapter() {
            @Override
            public void onSubscriptionData(SubscriptionData data) {
                for (AnyJson json : data.getMessages()) {
                  sender.send(new ProducerRecord("crypto",json));
                }
            }
        };

        client.createSubscription(channel, SubscriptionMode.SIMPLE, listener);

        client.start();
    }

}
