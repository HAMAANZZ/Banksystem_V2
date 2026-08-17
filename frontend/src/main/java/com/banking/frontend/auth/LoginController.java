package com.banking.frontend.auth;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import com.banking.frontend.auth.dto.LoginResponse;
import com.banking.frontend.dashboard.StartseiteController;
import tools.jackson.databind.ObjectMapper;


import com.banking.frontend.admin.AdminStartseiteController;
import com.banking.frontend.auth.dto.LoginResponse;
import com.banking.frontend.dashboard.StartseiteController;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import tools.jackson.databind.ObjectMapper;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

// Steuert die Login Oberfläche.
public class LoginController {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Label messageLabel;

    // Wird ausgeführt, wenn der Benutzer auf Login klickt.
    @FXML
    private void handleLogin() {

        // Eingaben aus den Feldern holen
        String username = usernameField.getText().trim();
        String password = passwordField.getText();

        // Alte Fehlermeldung löschen
        messageLabel.setText("");

        // Prüfen, ob Felder leer sind
        if (username.isBlank() || password.isBlank()) {
            messageLabel.setText("Bitte Benutzername und Passwort eingeben.");
            return;
        }

        try {

            // Login Daten für das Backend
            String json = """
                    {
                        "username": "%s",
                        "password": "%s"
                    }""".formatted(username, password);

            // HTTP Client
            HttpClient client = HttpClient.newHttpClient();

            // POST Anfrage an das Backend.
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("http://localhost:8080/api/auth/login"))
                    .header("Content-Type",
                            "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(json))
                    .build();

            // Anfrage senden an Backend => SecurityConfig.java
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            // Nur zum Testen
            System.out.println("Status: " + response.statusCode());
            System.out.println("Antwort: " + response.body());

            // Login erfolgreich
            if (response.statusCode() == 200) {

                messageLabel.setText("");

                // JSON Antwort des Backends in LoginResponse umwandeln.
                ObjectMapper mapper = new ObjectMapper();

                LoginResponse loginResponse = mapper.readValue(
                        response.body(),
                        LoginResponse.class
                );

                // Prüfen, welche Rolle der Benutzer hat.
                if ("ADMIN".equals(loginResponse.getRole())) {

                    // Admin bekommt die Admin Startseite.
                    openAdminStartseite(loginResponse);

                } else {

                    // Normaler Benutzer bekommt seine normale Startseite.
                    openStartseite(loginResponse);
                }

            } else {

                // Fehlermeldung aus dem Backend anzeigen.
                messageLabel.setText(response.body());
            }


        } catch (Exception exception) {

            messageLabel.setText("Keine Verbindung zum Backend.");
            exception.printStackTrace();
        }
    }


    // Öffnet die normale Benutzer Startseite.
    private void openStartseite(LoginResponse loginResponse) throws Exception {

        // FXML laden.
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/views/Startseite.fxml")
        );

        Parent root = loader.load();


        // Controller der Startseite holen.
        StartseiteController controller = loader.getController();


        // Daten des eingeloggten Benutzers übergeben.
        controller.setUserData(
                loginResponse.getUsername(),
                loginResponse.getRole(),
                loginResponse.getUserId()
        );


        // Aktuelles Fenster holen.
        Stage stage = (Stage) usernameField
                .getScene()
                .getWindow();


        // Login durch Benutzer Startseite ersetzen.
        stage.setScene(new Scene(root));

        stage.setTitle("Banksystem");

        stage.show();
    }

    // Öffnet die Admin Startseite.
    private void openAdminStartseite(LoginResponse loginResponse) throws Exception {

        // Admin FXML laden.
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/views/AdminStartseite.fxml")
        );

        Parent root = loader.load();


        // Controller der Admin Seite holen.
        AdminStartseiteController controller = loader.getController();


        // Admin Daten übergeben.
        controller.setAdminData(
                loginResponse.getUsername(),
                loginResponse.getRole()
        );


        // Aktuelles Fenster holen.
        Stage stage = (Stage) usernameField
                .getScene()
                .getWindow();


        // Login durch Admin Startseite ersetzen.
        stage.setScene(new Scene(root));

        stage.setTitle("Banksystem Admin");

        stage.show();
    }
}
