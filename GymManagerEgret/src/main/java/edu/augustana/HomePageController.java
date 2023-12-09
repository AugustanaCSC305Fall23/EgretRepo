package edu.augustana;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;


public class HomePageController {

    @FXML
    private ImageView imageBackground;

    @FXML
    private Label flowFlex;

    @FXML
    private Button planMaker;

    @FXML
    private Button courseView;


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
        flowFlex.getStyleClass().add("kaushan");
        planMaker.getStyleClass().add("alegreya");
        courseView.getStyleClass().add("alegreya");

    }
}