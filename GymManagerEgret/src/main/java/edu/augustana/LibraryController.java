package edu.augustana;

import java.io.IOException;


import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.image.ImageView;


public class LibraryController {

    @FXML
    private ListView<String> lessonList;

    @FXML
    private ImageView home;

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
        home.setImage(App.homeIcon());
    }


}
