package org.example.microservice.kafka;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Properties;

@Component
public class MyProducer {

    private static final Logger LOGGER = LoggerFactory.getLogger(MyProducer.class);
    //private static final String BOOTSTRAP_SERVERS = "kafka-1:9092,kafka-2:9092,kafka-3:9092";
    private static final String BOOTSTRAP_SERVERS = "kafka-1:9092";
    private final KafkaProducer<String, String> producer;

    public MyProducer() {
        this.producer = initProducer();
        LOGGER.info("producer was initiated {}", producer);
    }

    private KafkaProducer<String, String> initProducer() {
        Properties properties = new Properties();
        properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVERS);
        properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

        return new KafkaProducer<>(properties);
    }

    public void send(String topic, String message) {
        ProducerRecord<String, String> record =
                new ProducerRecord<>(topic, message);
        send(record);
    }

    public void send(ProducerRecord<String, String> record) {
        producer.send(record);
    }
}
