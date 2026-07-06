package com.arshi.todo_api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(IncorrectPasswordException.class)
    public ResponseEntity<Map<String, String>> handleIncorrectPasswordException(IncorrectPasswordException ex) {
        Map<String, String> error = new HashMap<>();
        error.put("message", ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(UserNameAlreadyExistsException.class)
    public ResponseEntity<Map<String, String>> handleUserNameAlreadyExistsException(UserNameAlreadyExistsException ex) {
        Map<String, String> error = new HashMap<>();
        error.put("message", ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(UserNameNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleUserNameNotFoundException(UserNameNotFoundException ex) {
        Map<String, String> error = new HashMap<>();
        error.put("message", ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(TodoNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleUserIdMismatchException(TodoNotFoundException ex) {
        Map<String, String> error = new HashMap<>();
        error.put("message", ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UnauthorizedAccessException.class)
    public ResponseEntity<Map<String, String>> handleUnauthorizedAccessException(UnauthorizedAccessException ex) {
        Map<String, String> error = new HashMap<>();
        error.put("message", ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.FORBIDDEN);
    }
}
