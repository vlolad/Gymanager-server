package ru.gymanager.server.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

// Класс для отправки юзеру данных об произошедшей ошибке
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ErrorResponse {
    private String message;
    private Exception exception;
    private List<Violation> violations = new ArrayList<>();

    public ErrorResponse(String message) {
        this.message = message;
    }

    public ErrorResponse(String message, Exception exception) {
        this.message = message;
        this.exception = exception;
    }
}
