package com.arshi.todo_api.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "Username cannot be blank")
    private String userName;
    @NotBlank(message = "Password cannot be blank")
    @Size(min = 6, message = "Password length should be minimum of 6 characters")
    private String password;
    @NotBlank(message = "Email cannot be blank")
    private String email;

    public User(){
    }

    public User(String username, String password, String email) {
        this.userName = username;
        this.password = password;
        this.email = email;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
