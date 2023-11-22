package edu.augustana;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;


public class HomePageController {

    @FXML
    private ImageView imageBackground;


    @FXML
    void connectToCourseLibrary() {
        App.switchToLibraryView();
    }

    @FXML
    void connectToPlanMaker() {
        App.switchToPlanMakerView();
    }

    @FXML
    void initialize() {
        imageBackground.setImage(App.backgroundImage());
    }

}



