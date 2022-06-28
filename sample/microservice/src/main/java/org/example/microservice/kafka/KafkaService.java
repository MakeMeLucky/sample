package org.example.microservice.kafka;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.microservice.kafka.model.Record;
import org.example.microservice.storage.PostgreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class KafkaService {

    @Autowired
    PostgreService postgreService;

    @Autowired
    private KafkaTemplate<String, Record> kafkaTemplate;

    private final Logger LOGGER = LogManager.getLogger(KafkaService.class);

    @KafkaListener(topics = "${kafka.topic}", groupId = "${kafka.group.id}")
    public void poll(Record record) {
        LOGGER.info("Got record {}", record);
        List<Record> child = record.getChildRecords();
        if (child != null) {
            child.forEach(children -> {
                sendMessage(children.getMicroserviceId(), children);
                postgreService.saveToDb(record, children);
            });
        } else {
            postgreService.saveToDb(record, new Record());
        }
    }

    public void sendMessage(String topicName, Record record) {
        if (topicName != null) {
            kafkaTemplate.send(topicName, record);
        }
    }
}
