package com.banking.backend.user.model;
//model= Wie sieht ein Benutzer aus?

import jakarta.persistence.*;

import java.time.LocalDateTime;

// Diese Klasse repräsentiert einen Benutzer in der Datenbank.
@Entity
// Der Benutzer wird in der Tabelle "users" gespeichert.
@Table(name = "users")
public class User {

    // Primärschlüssel des Benutzers.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Benutzername muss eindeutig sein.
    @Column(nullable = false, unique = true)
    private String username;

    // E Mail Adresse muss ebenfalls eindeutig sein.
    @Column(nullable = false, unique = true)
    private String email;

    // Hier wird später NICHT das Klartextpasswort gespeichert.
    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false)
    private String geburtstag;

    // Speichert die Rolle als Text, zum Beispiel USER.
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    // Speichert den Status als Text, zum Beispiel ACTIVE.
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserStatus status;

    // Zeitpunkt, zu dem der Benutzer erstellt wurde.
    @Column(nullable = false)
    private LocalDateTime createdAt;


    // JPA benötigt einen leeren Konstruktor.
    public User() {
    }


    // Wird automatisch ausgeführt, bevor ein neuer Benutzer gespeichert wird.
    @PrePersist
    public void beforeInsert() {

        // Neuer Benutzer ist standardmäßig ein normaler Benutzer.
        if (role == null) {
            role = Role.USER;
        }

        // Neuer Benutzer ist standardmäßig aktiv.
        if (status == null) {
            status = UserStatus.ACTIVE;
        }

        // Aktuelle Zeit speichern.
        if (createdAt == null) {
            createdAt = LocalDateTime.now();
        }
    }


    // Getter und Setter

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }


    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getGeburtstag() {
        return geburtstag;
    }

    public void setGeburtstag(String geburtstag) {
        this.geburtstag = geburtstag;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }


    public UserStatus getStatus() {
        return status;
    }

    public void setStatus(UserStatus status) {
        this.status = status;
    }


    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
