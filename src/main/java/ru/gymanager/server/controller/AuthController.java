package ru.gymanager.server.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.gymanager.server.security.AuthRequest;

import javax.validation.Valid;

@RestController
@Slf4j
//@CrossOrigin - #TODO нужна ли эта аннотация на данном этапе?
public class AuthController {

    @Autowired
    public AuthController() {

    }

    @PostMapping("/login")
    public ResponseEntity<?> createAuthToken(@RequestBody @Valid AuthRequest authRequest) {

    }
}
