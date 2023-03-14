package ru.gymanager.server.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
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

    @GetMapping("/user/hello")
    public ResponseEntity<Object> sayHelloToUser() {
        log.info("Catch request: GET at /hello/user");
        return new ResponseEntity<>(new SimpleMessage("Hello from Server to User!"), HttpStatus.OK);
    }

    @GetMapping("/admin/hello")
    public ResponseEntity<Object> sayHelloToAdmin() {
        boolean check = SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream()
                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ADMIN"));
        if (!check) {
            return new ResponseEntity<>(new SimpleMessage("NO RIGHTS"), HttpStatus.FORBIDDEN);
        }
        log.info("Catch request: GET at /hello/admin");
        return new ResponseEntity<>(new SimpleMessage("Hello from Server to Admin!"), HttpStatus.OK);
    }


}
