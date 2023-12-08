package edu.augustana;

import javafx.fxml.FXML;
import javafx.scene.image.ImageView;


public class HomePageController {

    @FXML
    private ImageView imageBackground;

    @FXML
    void connectToCourseLibrary() {
        App.switchCourseView();
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