package ru.gymanager.server.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class NotFoundException extends ResponseStatusException {
    public NotFoundException (String msg) {
        super(HttpStatus.NOT_FOUND, msg);
    }
}
