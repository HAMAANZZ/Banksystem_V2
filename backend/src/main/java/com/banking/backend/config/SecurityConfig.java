package com.banking.backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

// Konfiguriert die Sicherheit unserer REST API.
@Configuration
public class SecurityConfig {

    //Anfrage  ==>>  SecurityFilterChain  ==>>  erlaubt? ==>>  Controller
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        HttpSecurity httpSecurity = http

                // Unser JavaFX Client arbeitet mit einer REST API.
                // Deshalb deaktivieren wir CSRF für diese API.
                .csrf(csrf -> csrf.disable())

                // Legt fest, welche Anfragen erlaubt sind.
                .authorizeHttpRequests(auth -> auth

                        // Registrierung ist ohne Login erlaubt.
                        .requestMatchers(HttpMethod.POST, "/api/users")
                        .permitAll()

                        // Login ist ohne Login erlaubt.
                        .requestMatchers(HttpMethod.POST, "/api/auth/login")
                        .permitAll()

                        // Alles andere bleibt geschützt.
                        .anyRequest()
                        .authenticated()
                );

        // Erstellt die konfigurierte SecurityFilterChain.
        return http.build();
    }
}