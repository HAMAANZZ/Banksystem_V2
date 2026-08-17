package com.banking.backend.auth.dto;

import com.banking.backend.user.model.Role;

// Antwort, wenn der Login erfolgreich war.
public class LoginResponse {

    private Long userId;
    private String username;
    private Role role;


    public LoginResponse(Long userId, String username, Role role) {
        this.userId = userId;
        this.username = username;
        this.role = role;
    }


    public Long getUserId() {
        return userId;
    }

    public String getUsername() {
        return username;
    }

    public Role getRole() {
        return role;
    }
}