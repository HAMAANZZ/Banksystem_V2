package com.banking.backend.user.repository;
//repository= Zugriff auf die Datenbank

import com.banking.backend.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

// Schnittstelle für den Zugriff auf die users Tabelle.
public interface UserRepository extends JpaRepository<User, Long> {

    // Sucht einen Benutzer anhand seines Benutzernamens.
    Optional<User> findByUsername(String username);

    // Sucht einen Benutzer anhand seiner E Mail Adresse.
    Optional<User> findByEmail(String email);

    // Sucht einen Benutzer anhand seiner geburtstag
    Optional<User> findByGeburtstag(String geburtstag);

    // Prüft, ob ein Benutzername bereits existiert.
    boolean existsByUsername(String username);

    // Prüft, ob ein geburtstag bereits existiert.
    boolean existsByGeburtstag(String geburtstag);

    // Prüft, ob eine E Mail Adresse bereits existiert.
    boolean existsByEmail(String email);
}