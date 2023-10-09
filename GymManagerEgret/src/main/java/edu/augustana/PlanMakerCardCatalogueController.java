package edu.augustana;

import java.io.IOException;
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

        @FXML
        private void openSchedule() throws IOException {
                App.setRoot("schedulePage");
        }

}
