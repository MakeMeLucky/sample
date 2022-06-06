package org.example.application.kafka;


import org.example.application.microservices.model.Record;
import org.example.application.services.PostgreComponent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class KafkaService {

    @Autowired
    PostgreComponent postgreComponent;

    @Autowired
    private KafkaTemplate<String, Record> kafkaTemplate;

/*    @KafkaListener(topics = "${kafka.topic}", groupId = "${kafka.group.id}")
    public void poll(Record record) {
        System.out.println(record.getChildRecords());
    }*/

    public void sendMessage(String topicName, Record record) {
        kafkaTemplate.send(topicName, record);
    }
}
