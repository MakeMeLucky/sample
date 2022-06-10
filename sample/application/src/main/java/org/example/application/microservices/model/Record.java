package org.example.application.microservices.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
public class Record extends MicroService implements Serializable {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String id;

    private String microserviceId;

    private String microserviceName;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<Record> childRecords;

    public void addRecord(Record record) {
        if (childRecords == null) {
            childRecords = new ArrayList<>();
        }
        childRecords.add(record);
    }

}
