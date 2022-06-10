package org.example.microservice5.kafka.model;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class Record implements Serializable {

    private String id;

    private String microserviceId;

    private String microserviceName;

    private List<Record> childRecords;

}
