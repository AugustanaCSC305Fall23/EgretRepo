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


//<MenuItem fx:id="saveButton" mnemonicParsing="false" onAction="#saveMenuAction" text="Save" />
//<MenuItem fx:id="saveAsButton" mnemonicParsing="false" onAction="#saveAsMenuAction" text="Save As" />
// <MenuItem mnemonicParsing="false" onAction="#loadMenuAction" text="Open" />
//<MenuItem mnemonicParsing="false" onAction="#shareMenuAction" text="Share" />
