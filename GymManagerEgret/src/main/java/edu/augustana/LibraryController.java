package edu.augustana;


import javafx.fxml.FXML;
import javafx.scene.image.ImageView;


public class LibraryController {

    @FXML
    private ImageView home;

 //   @FXML
//    private void switchToSchedule() throws IOException {
//        App.setRoot("LessonViewPage");
//    }

    @FXML
    void connectToHomePage() {
        App.switchToHomePageView();
    }


    public void initialize() {
        home.setImage(App.homeIcon());
    }


}
