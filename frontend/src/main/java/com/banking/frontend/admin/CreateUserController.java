package com.banking.frontend.admin;

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

// Controller zum Anlegen eines neuen Benutzers.
public class CreateUserController {

    @FXML
    private TextField usernameField;
    @FXML
    private TextField emailField;
    @FXML
    private TextField firstNameField;
    @FXML
    private TextField lastNameField;
    @FXML
    private TextField geburtstagField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private PasswordField confirmPasswordField;
    @FXML
    private Label messageLabel;


    // Wird ausgeführt, wenn der Admin auf "Benutzer erstellen" klickt.
    @FXML
    private void handleCreateUser() {

        // Eingaben aus der Oberfläche holen.
        String username = usernameField.getText().trim();
        String email = emailField.getText().trim();
        String firstName = firstNameField.getText().trim();
        String lastName = lastNameField.getText().trim();
        String geburtstag = geburtstagField.getText().trim();
        String password = passwordField.getText();
        String confirmPassword = confirmPasswordField.getText();


        // Prüfen, ob alle Pflichtfelder ausgefüllt sind.
        if (username.isBlank()
                || email.isBlank()
                || firstName.isBlank()
                || lastName.isBlank()
                || password.isBlank()
                || geburtstag.isBlank()
                || confirmPassword.isBlank()) {

            messageLabel.setText("Bitte alle Felder ausfüllen.");

            return;
        }


        // Prüfen, ob beide Passwörter gleich sind.
        if (!password.equals(confirmPassword)) {

            messageLabel.setText("Die Passwörter stimmen nicht überein.");

            return;
        }


        try {

            // Daten als JSON für das Backend erstellen.
            String json = """
                    {
                        "username": "%s",
                        "email": "%s",
                        "firstName": "%s",
                        "lastName": "%s",
                        "geburtstag": "%s",
                        "password": "%s"
                    }
                    """.formatted(
                    username,
                    email,
                    firstName,
                    lastName,
                    geburtstag,
                    password
            );


            // HTTP Client erstellen.
            HttpClient client = HttpClient.newHttpClient();


            // POST Anfrage an das Backend erstellen.
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("http://localhost:8080/api/users"))
                    .header("Content-Type",
                            "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(json))
                    .build();


            // Anfrage senden.
            HttpResponse<String> response = client.send(
                    request,
                    HttpResponse.BodyHandlers.ofString());


            // Benutzer wurde erfolgreich erstellt.
            if (response.statusCode() == 201) {

                messageLabel.setText("Benutzer wurde erfolgreich erstellt.");

                clearFields();

            } else {

                // Fehlermeldung aus dem Backend anzeigen.
                messageLabel.setText(response.body());
            }

        } catch (Exception exception) {

            messageLabel.setText("Keine Verbindung zum Backend.");

            exception.printStackTrace();
        }
    }


    // Leert nach erfolgreicher Erstellung alle Eingabefelder.
    private void clearFields() {

        usernameField.clear();
        emailField.clear();
        firstNameField.clear();
        lastNameField.clear();
        geburtstagField.clear();
        passwordField.clear();
        confirmPasswordField.clear();
    }


    // Geht später zurück zur Admin Startseite.
    @FXML
    private void handleBack() {

        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/AdminStartseite.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) usernameField.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Banksystem Admin");
            stage.show();

        } catch (Exception exception) {

            exception.printStackTrace();
        }
    }
}