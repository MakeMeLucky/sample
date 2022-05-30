package org.example.application.microservices;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class ServiceProcess {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<ServiceProcess> serviceProcesses;

    private String source;

    private String target;

    public ServiceProcess() {
    }

    public ServiceProcess(String source, String target) {
        this.source = source;
        this.target = target;
    }
}
