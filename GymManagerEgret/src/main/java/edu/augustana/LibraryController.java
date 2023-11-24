package edu.augustana;


import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.image.ImageView;


public class LibraryController {

    @FXML
    private ImageView home;

    @FXML
    private ListView<LessonPlan> lessonList;

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
        lessonList.getItems().addAll(App.getCurrentCourse().getLessonPlans());
    }

}
