package ru.gymanager.gateway.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.gymanager.gateway.util.HelloClient;

@Slf4j
@RestController
public class HelloController {

    private final HelloClient helloClient;

    @Autowired
    public HelloController(HelloClient helloClient) {

        this.helloClient = helloClient;
    }

    @GetMapping("/hello")
    public ResponseEntity<Object> sayHello() {
        log.info("Catch request: GET at /hello");
        return new ResponseEntity<>("Hello from Gateway!", HttpStatus.OK);
    }

    @GetMapping("/server/hello")
    public ResponseEntity<Object> sayHelloFromServer() {
        log.info("Catch request: GET at /server/hello");
        return helloClient.getHello();
    }
}
