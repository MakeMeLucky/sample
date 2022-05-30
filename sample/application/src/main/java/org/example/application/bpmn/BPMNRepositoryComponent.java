package org.example.application.bpmn;

import org.example.application.bpmn.model.Definitions;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import java.io.IOException;

@Component
public class BPMNRepositoryComponent {

    public Definitions unmarshall(MultipartFile file) throws JAXBException, IOException {
        JAXBContext jaxbContext = JAXBContext.newInstance(Definitions.class);
        return (Definitions) jaxbContext.createUnmarshaller().unmarshal(file.getInputStream());
    }

}
