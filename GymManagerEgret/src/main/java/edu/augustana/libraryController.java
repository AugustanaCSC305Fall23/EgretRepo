package edu.augustana;

import java.io.IOException;
import javafx.fxml.FXML;

public class libraryController {

    @FXML
    private void switchToSchedule() throws IOException {
        App.setRoot("schedulePage");
    }
}
