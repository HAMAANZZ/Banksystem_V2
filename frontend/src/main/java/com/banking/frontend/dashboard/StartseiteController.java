package com.banking.frontend.dashboard;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.AnchorPane;

public class StartseiteController {

    @FXML
    private AnchorPane sceneStartseite;

    @FXML
    private ListView<String> listViewAcconts;

    @FXML
    private ContextMenu contextMenu;

    @FXML
    private MenuItem menuAuswaehlen;

    @FXML
    private MenuItem menuLoeschen;

    @FXML
    private Button AccountErstellen;

    @FXML
    private void initialize() {

        AccountErstellen.setOnAction(event -> {
            System.out.println("Account erstellen");
        });

        menuAuswaehlen.setOnAction(event -> {
            System.out.println("Account auswählen");
        });

        menuLoeschen.setOnAction(event -> {
            System.out.println("Account löschen");
        });
    }
}