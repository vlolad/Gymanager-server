package ru.gymanager.server.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.gymanager.server.model.SimpleMessage;

@Slf4j
@RestController
public class HelloController {

    @GetMapping("/hello")
    public ResponseEntity<Object> sayHello() {
        log.info("Catch request: GET at /hello");
        return new ResponseEntity<>(new SimpleMessage("Hello from Server!"), HttpStatus.OK);
    }


}
