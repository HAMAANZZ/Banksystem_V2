package com.banking.backend.auth.dto;

import jakarta.validation.constraints.NotBlank;

// Enthält die Daten, die beim Login gesendet werden.
public class LoginRequest {

    @NotBlank
    private String username;

    @NotBlank
    private String password;


    public LoginRequest() {
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }


    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}