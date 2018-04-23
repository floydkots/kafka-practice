import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.Properties;

public class KafkaProducerApp {
    public static void main(String[] args) {
        // Create a properties directory for the required/optional Producer config settings
        Properties props = new Properties();
        props.put("bootstrap.servers", "172.17.0.1:32773,172.17.0.1:32772, 172.17.0.1:32771");
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        // --> props.put("config.setting", "value")
        // :: http://kafka.apache.org/documentation.html#producerconfigs

        KafkaProducer<String, String> myProducer = new KafkaProducer<String, String>(props);

        try {
            for (int i = 0; i < 150; i++) {
                myProducer.send(new ProducerRecord<String, String>("my-topic", Integer.toString(i), "My Message: " + Integer.toString(i)));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            myProducer.close();
        }
    }
}
