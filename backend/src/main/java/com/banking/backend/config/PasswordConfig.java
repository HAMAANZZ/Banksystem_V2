package com.banking.backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

// Konfiguration für das sichere Speichern von Passwörtern.
@Configuration
public class PasswordConfig {

    // Erstellt einen PasswordEncoder, den Spring später verwenden kann.
    @Bean
    public PasswordEncoder passwordEncoder() {

        // BCrypt hasht das Passwort vor dem Speichern.
        return new BCryptPasswordEncoder();
    }
}