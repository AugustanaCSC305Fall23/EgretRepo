package edu.augustana;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;

public class CourseLibraryController {
    @FXML
    private Button home;
    @FXML
    private ImageView homeIcon;


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
        homeIcon.setImage(App.homeIcon());

    }


}
