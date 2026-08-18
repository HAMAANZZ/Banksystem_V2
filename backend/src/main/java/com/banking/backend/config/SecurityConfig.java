package com.banking.backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

// Konfiguriert die Sicherheit unserer REST API.
/*
 * Ablauf:
 * - Start: Spring ruft diese @Bean-Methode 1x auf und cached die erzeugte SecurityFilterChain.
 * - Runtime: Jede eingehende Anfrage (z.B. POST /api/auth/login) durchläuft diese
 *   gecachte Kette. Dabei werden die definierten Matcher (z.B. permitAll())
 *   zur Laufzeit gegen den tatsächlichen Request geprüft.
 */
@Configuration
public class SecurityConfig {

    //Anfrage  ==>>  SecurityFilterChain, erlaubt? ==>>  Controller
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        HttpSecurity httpSecurity = http

                // Unser JavaFX Client arbeitet mit einer REST API.
                // Deshalb deaktivieren wir CSRF für diese API.
                .csrf(csrf -> csrf.disable())

                // Legt fest, welche Anfragen erlaubt sind.
                .authorizeHttpRequests(auth -> auth

                        // Registrierung ist ohne Login erlaubt.
                        .requestMatchers(HttpMethod.POST, "/api/users").permitAll()

                        // Login ist ohne Login erlaubt.
                        .requestMatchers(HttpMethod.POST, "/api/auth/login").permitAll()

                        // Benutzerliste laden
                        // NUR vorübergehend für die Entwicklung
                        //SPÄTER MIT TOCKEN Prüfen ob es sich um einen Admin handelt
                        .requestMatchers(HttpMethod.GET, "/api/users").permitAll()

                        .requestMatchers(HttpMethod.GET, "/api/accounts/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/accounts/**").permitAll()

                        // Alles andere bleibt geschützt.
                        .anyRequest()
                        .authenticated()
                );

        // Erstellt die konfigurierte SecurityFilterChain.
        return http.build();
    }
}