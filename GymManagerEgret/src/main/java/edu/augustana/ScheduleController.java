package edu.augustana;

import java.io.IOException;
import javafx.fxml.FXML;

public class ScheduleController {

    @FXML
    private void switchToLibrary() throws IOException {
        App.setRoot("libraryPage");
    }

    @FXML
    private void openCards() throws IOException {
        App.setRoot("PlanMakerCardPage");
    }


}