package com.banking.frontend.account;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.AnchorPane;

public class AccountViewController {

    @FXML
    private AnchorPane sceneAccountview;

    @FXML
    private ListView<String> TrasaktionenListeView;

    @FXML
    private ContextMenu contextMenu;

    @FXML
    private MenuItem menuSortAscending;

    @FXML
    private MenuItem menuSortDescending;

    @FXML
    private MenuItem menuShowPositive;

    @FXML
    private MenuItem menuShowNegative;

    @FXML
    private MenuItem menuLoeschen;

    @FXML
    private Label AccountDetails;

    @FXML
    private Button BackKnopf;

    @FXML
    private Button PaymentErstellen;

    @FXML
    private Button TransferErstellen;

    @FXML
    private void initialize() {

        AccountDetails.setText("Kontodetails");

        BackKnopf.setOnAction(event -> {
            System.out.println("Zurück");
        });

        PaymentErstellen.setOnAction(event -> {
            System.out.println("Payment erstellen");
        });

        TransferErstellen.setOnAction(event -> {
            System.out.println("Transfer erstellen");
        });
    }
}