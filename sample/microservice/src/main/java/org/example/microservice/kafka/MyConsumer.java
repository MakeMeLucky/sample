package org.example.microservice.kafka;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.*;

@Component
public class MyConsumer {

    private static final Logger LOGGER = LoggerFactory.getLogger(MyConsumer.class);
    //private static final String BOOTSTRAP_SERVERS = "kafka-1:9092,kafka-2:9092,kafka-3:9092";
    private static final String BOOTSTRAP_SERVERS = "kafka-1:9092";
    private static final String GROUP_ID = "first-group";
    private static final String DEFAULT_TOPIC = "my-topic-1";
    private final KafkaConsumer<String, String> consumer;

    public MyConsumer() {
        this.consumer = initConsumer();
        subscribeToTopic(DEFAULT_TOPIC);
    }

    private KafkaConsumer<String, String> initConsumer() {
        Properties properties = new Properties();
        properties.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVERS);
        properties.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.setProperty(ConsumerConfig.GROUP_ID_CONFIG, GROUP_ID);
        properties.setProperty(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");

        return new KafkaConsumer<>(properties);
    }

    public void subscribeToTopic(String topic) {
        consumer.subscribe(Collections.singletonList(topic));
    }

    public List<String> poll() {
        List<String> result = new ArrayList<>();
        LOGGER.info("ENTER POLL");
        //while(true){
            ConsumerRecords<String, String> records =
                    consumer.poll(Duration.ofMillis(100));

            for (ConsumerRecord<String, String> record : records){
                LOGGER.info("Key: " + record.key() + ", Value: " + record.value());
                LOGGER.info("Partition: " + record.partition() + ", Offset:" + record.offset());
                result.add("key: " + record.key() + "; value: " + record.value());
            }

            return result;
        }
    //}
}
