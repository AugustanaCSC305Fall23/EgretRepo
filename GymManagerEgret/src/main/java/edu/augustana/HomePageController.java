package edu.augustana;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

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
    void connectToLibrary(ActionEvent event) {
        App.switchToMainView();
    }

    @FXML
    void connectToPlanMaker(ActionEvent event){
        App.switchToPlanMaker();
    }

    @FXML
    void initialize() {
        assert lessonLibrary != null : "fx:id=\"lessonLibrary\" was not injected: check your FXML file 'HomePage.fxml'.";

    }

}
