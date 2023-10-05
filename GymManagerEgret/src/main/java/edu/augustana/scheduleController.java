package edu.augustana;

import java.io.IOException;
import javafx.fxml.FXML;

public class scheduleController {

    @FXML
    private void switchToLibrary() throws IOException {
        App.setRoot("libraryPage");
    }
}