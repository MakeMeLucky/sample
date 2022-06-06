package org.example.application.services;

import org.example.application.bpmn.BPMNRepositoryComponent;
import org.example.application.bpmn.model.Definitions;
import org.example.application.microservices.converters.RecordConverter;
import org.example.application.microservices.model.Record;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.xml.bind.JAXBException;
import java.io.IOException;

@Component
public class AppService {

    @Autowired
    private BPMNRepositoryComponent bpmnRepository;

    @Autowired
    private RecordConverter recordConverter;

    public Record create(MultipartFile file) throws JAXBException, IOException {
        Definitions definitions = bpmnRepository.unmarshall(file);
        return recordConverter.convert(definitions);
    }

}
