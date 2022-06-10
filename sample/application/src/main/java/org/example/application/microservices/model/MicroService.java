package org.example.application.microservices.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class MicroService {

    private String microserviceId;

    private String microserviceName;

    public MicroService() {
    }

    public MicroService(String microserviceId, String microserviceName) {
        this.microserviceId = microserviceId;
        this.microserviceName = microserviceName;
    }


}
