package com.arshi.todo_api.controller;

import com.arshi.todo_api.model.User;
import com.arshi.todo_api.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping({"/api/auth"})
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public User registerUser(@Valid @RequestBody User user) {
        return userService.registerUser(user);
    }

    @PostMapping("/login")
    public String loginUser(@RequestBody User loginRequest) {
        return userService.loginUser(loginRequest.getUserName(), loginRequest.getPassword());
    }

}
