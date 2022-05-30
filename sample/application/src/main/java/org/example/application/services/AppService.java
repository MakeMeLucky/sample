package org.example.application.services;

import org.example.application.bpmn.BPMNRepositoryComponent;
import org.example.application.bpmn.model.Definitions;
import org.example.application.converters.ServiceConverter;
import org.example.application.microservices.ServiceProcess;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.util.List;

@Component
public class AppService {

    @Autowired
    private BPMNRepositoryComponent bpmnRepository;

    @Autowired
    private ServiceConverter serviceConverter;

    public List<ServiceProcess> create(MultipartFile file) throws JAXBException, IOException {
        Definitions definitions = bpmnRepository.unmarshall(file);
        return serviceConverter.convert(definitions);
    }

}
