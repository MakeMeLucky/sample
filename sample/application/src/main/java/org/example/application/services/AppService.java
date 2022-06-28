package org.example.application.services;

import org.example.application.bpmn.BPMNConverter;
import org.example.application.bpmn.model.Definitions;
import org.example.application.microservices.converters.RecordConverter;
import org.example.application.microservices.model.DbRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Component
public class AppService {

    @Autowired
    private BPMNConverter bpmnConverter;

    @Autowired
    private RecordConverter recordConverter;

    @Autowired
    private PostgreService postgreService;

    public String create(MultipartFile file) throws Exception {
        Definitions definitions = bpmnConverter.unmarshall(file);
        return recordConverter.convert(definitions);
    }

    public List<DbRecord> getHistoryById(String id) {
        return postgreService.getHistoryById(id);
    }
}
