package edu.augustana;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;


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
    private ImageView imageBackground;


    @FXML
    void connectToCourseLibrary(ActionEvent event) {
        App.switchToLibraryView();
    }

    @FXML
    void connectToPlanMaker(ActionEvent event) {
        App.switchToPlanMakerView();
    }

    @FXML
    void initialize() {
        imageBackground.setImage(App.backgroundImage());
    }

}



