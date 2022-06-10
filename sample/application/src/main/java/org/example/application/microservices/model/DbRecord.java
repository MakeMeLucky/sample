package org.example.application.microservices.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.sql.Timestamp;

@Getter
@Setter
@ToString
public class DbRecord {

    private String sourceMicroserviceId;

    private String sourceMicroserviceName;

    private String targetMicroserviceId;

    private String targetMicroserviceName;

    private Timestamp timestamp;

}
