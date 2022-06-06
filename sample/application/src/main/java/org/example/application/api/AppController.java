package org.example.application.api;

import org.example.application.microservices.model.Record;
import org.example.application.services.AppService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.xml.bind.JAXBException;
import java.io.IOException;

@RestController
public class AppController {

    @Autowired
    private AppService appService;

    @RequestMapping(value = "create",
            method = RequestMethod.POST,
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Record> create(@RequestParam("file") MultipartFile file) throws IOException, JAXBException {
        return ResponseEntity.ok(appService.create(file));
    }

}
