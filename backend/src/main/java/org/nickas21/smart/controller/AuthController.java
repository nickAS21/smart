package org.nickas21.smart.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.Map;

@RestController()
@CrossOrigin(origins =  {"${app.dev.frontend.local}"})
public class MainController {

    @RequestMapping(value = "/api/message", produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, String> index() {
        return Collections.singletonMap("message", "Greetings from Spring Boot!");
    }
}
