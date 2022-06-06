package org.example.microservice7.kafka;

import org.example.microservice7.kafka.model.Record;
import org.example.microservice7.sql.PostgreComponent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class KafkaService {

    @Autowired
    PostgreComponent postgreComponent;

    @Autowired
    private KafkaTemplate<String, Record> kafkaTemplate;

    @KafkaListener(topics = "${kafka.topic}", groupId = "${kafka.group.id}")
    public void poll(Record record) {
        postgreComponent.saveToDb(record.getId());
        List<Record> child = record.getChildRecords();
        if (child != null) {
            child.forEach(children -> {
                sendMessage(children.getId(), children);
                postgreComponent.saveToDb(record.getId(), children.getId());
            });
        }
    }

    public void sendMessage(String topicName, Record record) {
        if (topicName != null) {
            kafkaTemplate.send(topicName, record);
        }
    }
}
