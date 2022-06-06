package org.example.microservice5;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AppController {
    @RequestMapping("/")
    public String hello() {
        return "Hello World!";
    }
}
