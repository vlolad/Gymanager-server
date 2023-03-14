package ru.gymanager.server.exception;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.MalformedJwtException;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.internal.engine.path.PathImpl;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.naming.AuthenticationException;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@ControllerAdvice
public class ErrorHandler extends ResponseEntityExceptionHandler {

    private Map<String, Object> buildResponseBody(Exception exc, String exceptionId) {
        Map<String, Object> body = new HashMap<>();
        body.put("exception_id", exceptionId);
        body.put("message", exc.getMessage());
        body.put("exception", exc.getClass());
        return body;
    }
    @ExceptionHandler
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ErrorResponse badToken(MalformedJwtException exc) {
        log.warn(exc.getMessage());
        return new ErrorResponse("Bad token", exc);
    }

    @ExceptionHandler(ExpiredJwtException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ErrorResponse tokenExpired(ExpiredJwtException exc) {
        log.warn(exc.getMessage());
        return new ErrorResponse("Token expired");
    }

    @ExceptionHandler(JwtException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ErrorResponse wrongToken(JwtException exc) {
        log.warn(exc.getMessage());
        return new ErrorResponse("Bad token", exc);
    }

    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ErrorResponse accessDeniedException(AccessDeniedException exc) {
        log.warn(exc.getMessage());
        return new ErrorResponse("Access denied", exc);
    }

    @ExceptionHandler(AuthenticationException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ErrorResponse authException(AuthenticationException exc) {
        log.warn(exc.getMessage());
        return new ErrorResponse("Current user not authenticated");
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<Object> handleNotFoundException(NotFoundException exc) {
        log.warn("Handle NotFoundException: {}", exc.getMessage());
        return new ResponseEntity<>(buildResponseBody(exc, "404"), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<Object> handleBadRequestException(BadRequestException exc) {
        log.warn("Handle BadRequestException: {}", exc.getMessage());
        return new ResponseEntity<>(buildResponseBody(exc, "400"), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleConstraintViolationException(final ConstraintViolationException exc) {
        log.warn("Handle ConstraintViolationException (@Valid in controller): {}", exc.getMessage());
        ErrorResponse error = new ErrorResponse("Some parameters didn't passed validation");
        for (ConstraintViolation violation : exc.getConstraintViolations()) {
            error.getViolations().add(
                    new Violation(((PathImpl) violation.getPropertyPath()).getLeafNode().getName(),
                            violation.getMessage()));
        }
        return error;
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResponse handleDataIntegrityViolationException(final DataIntegrityViolationException exc) {
        log.error("DataIntegrityViolationException: {}", exc.getMessage());
        return new ErrorResponse(exc.getMessage(), exc);
    }

    @ExceptionHandler(IOException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse ioException(IOException exc) {
        log.warn(exc.getMessage());
        return new ErrorResponse(exc.getMessage(), exc);
    }
}
