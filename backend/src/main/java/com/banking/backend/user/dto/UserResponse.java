package com.banking.backend.user.dto;

import com.banking.backend.user.model.Role;
import com.banking.backend.user.model.UserStatus;

import java.time.LocalDateTime;

/**
 * Ganz wichtig:
 * CreateUserRequest  ==>> enthält Passwort
 * aber:
 * UserResponse  ==>> enthält KEIN Passwort
 *
 * UserResponse ==>> JSON
 */
// Daten eines Benutzers, die an das Frontend gesendet werden dürfen.
// Nur Getter, kein Setter
public class UserResponse {

    private Long id;
    private String username;
    private String email;
    private String firstName;
    private String lastName;
    private String geburtstag;
    private Role role;
    private UserStatus status;
    private LocalDateTime createdAt;


    public UserResponse(
            Long id,
            String username,
            String email,
            String firstName,
            String lastName,
            String geburtstag,
            Role role,
            UserStatus status,
            LocalDateTime createdAt
    ) {

        this.id = id;
        this.username = username;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.geburtstag = geburtstag;
        this.role = role;
        this.status = status;
        this.createdAt = createdAt;
    }

    public String getGeburtstag() {
        return geburtstag;
    }

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public Role getRole() {
        return role;
    }

    public UserStatus getStatus() {
        return status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}