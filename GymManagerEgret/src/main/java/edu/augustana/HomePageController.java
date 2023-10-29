package edu.augustana;

import java.net.URL;
import java.util.ResourceBundle;

import edu.augustana.App;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.StackPane;



public class HomePageController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button lessonLibrary;

    @FXML
    private Button planMaker;


    @FXML
    void connectToCourseLibrary(ActionEvent event) {
        App.switchToCourseLibraryView();
    }

    @FXML
    void connectToPlanMaker(ActionEvent event) {
        App.switchToPlanMakerView();
    }

    @FXML
    void initialize() {

    }

}



