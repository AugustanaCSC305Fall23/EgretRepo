package edu.augustana;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;

public class PlanMakerCardCatalogueController {

        @FXML
        private ResourceBundle resources;

        @FXML
        private URL location;

        @FXML
        void initialize() {

        }
        @FXML
        void connectToHomePage() {
                App.switchToHomeView();
        }

}
