package ru.gymanager.trainingserver.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class ResourceController {

    @GetMapping("/resource")
    public String getResource() {
        log.info("Catch request: GET at /resource");
        return "Resource found, nice job!";
    }
}
