package edu.augustana;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class scheduleController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button cardsButton;

    @FXML
    private Button homePage;

    @FXML
    void switchToLibrary() throws IOException {
        App.setRoot("libraryPage");
    }

    @FXML
    void openCards() throws IOException {
        App.setRoot("PlanMakerCardPage");
    }

    @FXML
    void initialize() {

    }

}