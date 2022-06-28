package org.example.application.api;

import org.example.application.microservices.model.DbRecord;
import org.example.application.services.AppService;
import org.example.application.services.ClusterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.xml.bind.JAXBException;
import java.util.Collections;
import java.util.List;

@RestController
public class AppController {

    @Autowired
    private AppService appService;

    @Autowired
    private ClusterService clusterService;

    @RequestMapping(value = "create",
            method = RequestMethod.POST,
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<String> create(@RequestParam("file") MultipartFile file) throws Exception {
        return ResponseEntity.ok(appService.create(file));
    }

    @RequestMapping(value = "check",
            method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<List<DbRecord>> getHistoryById(@RequestParam("id") String id) {
        return ResponseEntity.ok(appService.getHistoryById(id));
    }

    @ExceptionHandler({ JAXBException.class})
    public ResponseEntity<String> handleJAXBException() {
        return new ResponseEntity<>("Incorrect xml", null, HttpStatus.BAD_REQUEST);
    }

}
