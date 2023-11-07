package edu.augustana;

import java.io.IOException;


import javafx.fxml.FXML;


public class LibraryController {

    @FXML
    private void switchToSchedule() throws IOException {
        App.setRoot("LessonViewPage");
    }

    @FXML
    void connectToCourseLibraryPage() {
        App.switchToCourseLibraryView();
    }


    public void initialize() {

    }


}
