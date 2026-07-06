package com.arshi.todo_api.exception;

public class UserNameNotFoundException extends RuntimeException {
    public UserNameNotFoundException(String message) {
        super(message);
    }
}
