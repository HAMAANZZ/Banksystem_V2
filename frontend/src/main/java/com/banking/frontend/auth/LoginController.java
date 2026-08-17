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

            if (response.statusCode() == 200) {

                messageLabel.setText("");

                // JSON Antwort des Backends in LoginResponse umwandeln.
                ObjectMapper objectMapper = new ObjectMapper();

                LoginResponse loginResponse = objectMapper.readValue(
                        response.body(),
                        LoginResponse.class
                );

                // Startseite öffnen und Benutzerdaten mitgeben.
                openStartseite(loginResponse);

            } else {

                // Fehlermeldung direkt vom Backend anzeigen.
                messageLabel.setText(response.body());
            }


        } catch (Exception exception) {

            messageLabel.setText("Keine Verbindung zum Backend.");
            exception.printStackTrace();
        }
    }

    // Öffnet nach erfolgreichem Login die Startseite.
    private void openStartseite(LoginResponse loginResponse) throws Exception {

        // Startseite laden.
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/Startseite.fxml"));

        Parent root = loader.load();

        // Den Controller der geladenen Startseite holen.
        StartseiteController controller = loader.getController();

        // Benutzername und Rolle an die Startseite übergeben.
        controller.setUserData(loginResponse.getUsername(),
                loginResponse.getRole(), loginResponse.getUserId());

        // Aktuelles Fenster holen.
        Stage stage = (Stage) usernameField
                .getScene()
                .getWindow();

        // Login durch Startseite ersetzen.
        stage.setScene(new Scene(root));

        stage.setTitle("Banksystem");
        stage.show();
    }
}