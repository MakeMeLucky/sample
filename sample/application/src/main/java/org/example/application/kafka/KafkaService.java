package org.example.application.kafka;


import org.example.application.microservices.model.Record;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class KafkaService {

    @Autowired
    private KafkaTemplate<String, Record> kafkaTemplate;

    public void sendMessage(String topicName, Record record) {
        kafkaTemplate.send(topicName, record);
    }
}
