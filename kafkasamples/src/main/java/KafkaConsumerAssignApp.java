import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.TopicPartition;

import java.util.ArrayList;
import java.util.Properties;

public class KafkaConsumerAssignApp {
    public static void main(String[] args) {
        // Create a properties dictionary for the required/optional Producer config settings
        Properties props = new Properties();
        props.put("bootstrap.servers", "172.17.0.1:32768");
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("group.id", "test");

        KafkaConsumer myConsumer = new KafkaConsumer(props);

        ArrayList<TopicPartition> partitions = new ArrayList<TopicPartition>();
        TopicPartition myTopic1Part0 = new TopicPartition("my-topic-1", 0);
        TopicPartition myTopic2Part2 = new TopicPartition("my-topic-2", 2);


        partitions.add(myTopic1Part0);
        partitions.add(myTopic2Part2);

        myConsumer.assign(partitions);

        try {
            while (true) {
                ConsumerRecords<String, String> records = myConsumer.poll(10);
                for (ConsumerRecord<String, String> record: records) {
                    // Process each record;
                    System.out.println
                            (String.format("Topic: %s, Partition: %d, Offset: %d, Key: %s, Value: %s",
                                    record.topic(), record.partition(), record.offset(), record.key(), record.value()));
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            myConsumer.close();
        }
    }
}
