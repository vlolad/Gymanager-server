package ru.gymanager.server.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.gymanager.server.security.JwtTokenUtil;
import ru.gymanager.server.security.model.AuthRequest;
import ru.gymanager.server.security.model.AuthResponse;

import javax.validation.Valid;

@RestController
@Slf4j
//@CrossOrigin - #TODO нужна ли эта аннотация на данном этапе?
public class AuthController {

    private final JwtTokenUtil jwtTokenUtil;
    private final AuthenticationManager authenticationManager;
    @Autowired
    public AuthController(JwtTokenUtil jwtTokenUtil, AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenUtil = jwtTokenUtil;
    }

    @PostMapping("/login")
    public ResponseEntity<?> createAuthToken(@RequestBody @Valid AuthRequest authRequest) {
        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequest.getLogin(), authRequest.getPassword()));
        if (!auth.isAuthenticated()) {
            throw new BadCredentialsException("Wrong user credits");
        }

        String token = jwtTokenUtil.generateToken(authRequest.getLogin());
        return ResponseEntity.ok(new AuthResponse(token));
    }
}
