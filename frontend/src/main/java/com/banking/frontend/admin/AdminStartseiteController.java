package com.banking.frontend.admin;

import com.banking.frontend.admin.dto.UserResponse;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

import tools.jackson.databind.ObjectMapper;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

// Controller für die Admin Startseite.
public class AdminStartseiteController {

    @FXML
    private Label adminNameLabel;

    @FXML
    private Label roleLabel;


    // Tabelle mit allen Benutzern.
    @FXML
    private TableView<UserResponse> userTable;


    @FXML
    private TableColumn<UserResponse, Long> idColumn;

    @FXML
    private TableColumn<UserResponse, String> usernameColumn;

    @FXML
    private TableColumn<UserResponse, String> firstNameColumn;

    @FXML
    private TableColumn<UserResponse, String> lastNameColumn;

    @FXML
    private TableColumn<UserResponse, String> emailColumn;

    @FXML
    private TableColumn<UserResponse, String> geburtstagColumn;

    @FXML
    private TableColumn<UserResponse, String> roleColumn;

    @FXML
    private TableColumn<UserResponse, String> statusColumn;


    @FXML
    private Button createUserButton;

    @FXML
    private Button refreshButton;

    @FXML
    private Button logoutButton;


    // Wird automatisch ausgeführt, nachdem die FXML geladen wurde.
    @FXML
    private void initialize() {

        // Legt fest, welches User Feld in welcher Spalte angezeigt wird.
        configureColumns();

        // Benutzer direkt beim Öffnen der Seite laden.
        loadUsers();
    }


    // Verbindet die Tabellenspalten mit den Benutzerdaten.
    private void configureColumns() {

        idColumn.setCellValueFactory(data ->
                new ReadOnlyObjectWrapper<>(
                        data.getValue().getId()
                )
        );

        usernameColumn.setCellValueFactory(data ->
                new SimpleStringProperty(
                        data.getValue().getUsername()
                )
        );

        firstNameColumn.setCellValueFactory(data ->
                new SimpleStringProperty(
                        data.getValue().getFirstName()
                )
        );

        lastNameColumn.setCellValueFactory(data ->
                new SimpleStringProperty(
                        data.getValue().getLastName()
                )
        );

        emailColumn.setCellValueFactory(data ->
                new SimpleStringProperty(
                        data.getValue().getEmail()
                )
        );

        geburtstagColumn.setCellValueFactory(data ->
                new SimpleStringProperty(
                        data.getValue().getGeburtstag()
                )
        );

        roleColumn.setCellValueFactory(data ->
                new SimpleStringProperty(
                        data.getValue().getRole()
                )
        );

        statusColumn.setCellValueFactory(data ->
                new SimpleStringProperty(
                        data.getValue().getStatus()
                )
        );
    }


    // Lädt alle Benutzer vom Backend.
    private void loadUsers() {

        try {

            // HTTP Client erstellen.
            HttpClient client = HttpClient.newHttpClient();


            // GET Anfrage an das Backend.
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(
                            URI.create(
                                    "http://localhost:8080/api/users"
                            )
                    )
                    .GET()
                    .build();


            // Anfrage senden.
            HttpResponse<String> response = client.send(
                    request,
                    HttpResponse.BodyHandlers.ofString()
            );


            // Benutzer erfolgreich geladen.
            if (response.statusCode() == 200) {

                // JSON in Java Benutzer umwandeln.
                ObjectMapper mapper = new ObjectMapper();

                UserResponse[] users = mapper.readValue(
                        response.body(),
                        UserResponse[].class
                );


                // Alte Tabelle leeren.
                userTable.getItems().clear();


                // Benutzer in Tabelle einfügen.
                userTable.getItems().addAll(users);
            } else {

                System.out.println(
                        "Benutzer konnten nicht geladen werden."
                );

                System.out.println(
                        response.body()
                );
            }

        } catch (Exception exception) {

            exception.printStackTrace();
        }
    }


    // Wird beim Klick auf Aktualisieren ausgeführt.
    @FXML
    private void handleRefresh() {

        // Benutzer erneut aus PostgreSQL laden.
        loadUsers();
    }


    // Öffnet das Formular zum Erstellen eines Benutzers.
    @FXML
    private void handleCreateUser() {

        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/CreateUser.fxml"));

            Parent root = loader.load();


            // Aktuelles Fenster holen.
            Stage stage = (Stage) createUserButton
                    .getScene()
                    .getWindow();


            // Admin Seite durch Benutzerformular ersetzen.
            stage.setScene(new Scene(root));
            stage.setTitle("Benutzer anlegen");
            stage.show();

        } catch (Exception exception) {

            exception.printStackTrace();
        }
    }


    // Meldet den Admin ab.
    @FXML
    private void handleLogout() {

        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/Login.fxml"));
            Parent root = loader.load();

            Stage stage = (Stage) logoutButton
                    .getScene()
                    .getWindow();

            // Admin Seite durch Login ersetzen.
            stage.setScene(new Scene(root));
            stage.setTitle("Banksystem Login");
            stage.show();

        } catch (Exception exception) {

            exception.printStackTrace();
        }
    }


    // Wird nach erfolgreichem Admin Login aufgerufen.
    public void setAdminData(
            String username,
            String role
    ) {

        adminNameLabel.setText(
                "Admin: " + username
        );

        roleLabel.setText(
                "Rolle: " + role
        );
    }
}