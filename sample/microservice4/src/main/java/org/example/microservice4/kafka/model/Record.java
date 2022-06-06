package org.example.microservice4.kafka.model;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
public class Record implements Serializable {

    private String id;

    private String realName;

    private List<Record> childRecords;

    public void addRecord(Record record) {
        if (childRecords == null) {
            childRecords = new ArrayList<>();
        }
        childRecords.add(record);
    }

}
