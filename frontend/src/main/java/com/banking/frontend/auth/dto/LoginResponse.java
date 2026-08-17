package com.banking.frontend.auth.dto;

// Enthält die Antwort des Backends nach erfolgreichem Login.
public class LoginResponse {

    private Long userId;
    private String username;
    private String role;

    // Wird von Jackson benötigt.
    public LoginResponse() {
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}