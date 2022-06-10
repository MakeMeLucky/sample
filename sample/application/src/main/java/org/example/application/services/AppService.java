package org.example.application.services;

import org.example.application.bpmn.BPMNRepositoryComponent;
import org.example.application.bpmn.model.Definitions;
import org.example.application.microservices.converters.RecordConverter;
import org.example.application.microservices.model.DbRecord;
import org.example.application.microservices.model.MicroService;
import org.example.application.microservices.model.Record;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class AppService {

    @Autowired
    private BPMNRepositoryComponent bpmnRepository;

    @Autowired
    private RecordConverter recordConverter;

    @Autowired
    private PostgreComponent postgreComponent;

    public String create(MultipartFile file) throws Exception {
        Definitions definitions = bpmnRepository.unmarshall(file);
        return recordConverter.convert(definitions);
    }

    public List<DbRecord> getHistoryById(String id) {
        return postgreComponent.getHistoryById(id);
    }
}
