package com.arshi.todo_api.exception;

public class UserNameAlreadyExistsException extends RuntimeException {
    public UserNameAlreadyExistsException(String message) {
        super(message);
    }
}
