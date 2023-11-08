package edu.augustana;

import java.io.IOException;


import javafx.fxml.FXML;
import javafx.scene.control.ListView;


public class LibraryController {

    @FXML
    private ListView<String> lessonList;

    @FXML
    private void switchToSchedule() throws IOException {
        App.setRoot("LessonViewPage");
    }

    @FXML
    void connectToCourseLibraryPage() {
        //change back later
        App.switchToHomePageView();
    }


    public void initialize() {
//        String lessonTitle = Course.currentLessonPlan.getTitle();
//        lessonList.getItems().add(lessonTitle);
    }


}
