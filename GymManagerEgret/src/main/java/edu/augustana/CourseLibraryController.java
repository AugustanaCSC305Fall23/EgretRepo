package edu.augustana;

import java.io.IOException;
import javafx.fxml.FXML;

public class CourseLibraryController {

    @FXML
    private void switchToSchedule() throws IOException {
        App.setRoot("LessonViewPage");
    }

    @FXML
    void connectToHomePage() {
        App.switchToHomePageView();
    }

    @FXML
    void initialize() {

    }


}