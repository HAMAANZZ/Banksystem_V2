package com.banking.backend.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

// Wandelt Backend Fehler in einfache Textantworten um.
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<String> handleResponseStatusException(
            ResponseStatusException exception
    ) {

        // Gibt genau den Text aus dem AuthService zurück.
        return ResponseEntity
                .status(exception.getStatusCode())
                .body(exception.getReason());
    }
}