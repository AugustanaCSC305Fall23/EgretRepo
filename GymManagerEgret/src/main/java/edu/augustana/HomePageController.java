package edu.augustana;

import java.net.URL;
import java.util.ResourceBundle;

import edu.augustana.App;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
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
    private ImageView backgroundImg;


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
        backgroundImg.setImage(App.backgroundImage());
    }

}



