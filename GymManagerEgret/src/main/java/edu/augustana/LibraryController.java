package edu.augustana;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Window;

import java.io.File;
import java.io.IOException;


public class LibraryController {

    @FXML
    private ImageView home;

    @FXML
    private ListView<LessonPlan> lessonList;

    private final LessonPlan currentLessonPlan;

    public LibraryController() {
        currentLessonPlan = App.getCurrentCourse().getCurrentLessonPlan();
    }

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

    @FXML
    private void actionAddNewLessonPlan() throws IOException {
        LessonPlan newLessonPlan = new LessonPlan();
        App.switchToEditLessonPlan(newLessonPlan, true);
    }

    @FXML
    private void actionEditExistingLessonPlan() throws IOException {
        App.switchToEditLessonPlan(currentLessonPlan, false);
    }

    @FXML
    private void deleteLessonPlan(ActionEvent event) {
        // Get the selected game from the ListView
        LessonPlan selectedLessonPlan = lessonList.getSelectionModel().getSelectedItem();
        if(selectedLessonPlan != null){
            lessonList.getItems().remove(selectedLessonPlan);
            App.getCurrentCourse().removeLessonPlan(selectedLessonPlan);
        } else {
            new Alert(Alert.AlertType.WARNING, "Select a game to delete first!").show();
        }
    }


//    @FXML
//    private void saveMenuAction(ActionEvent event) {
//        if(App.getCurrentCourseFile() == null){
//            Alert saveAlert = new Alert(Alert.AlertType.INFORMATION, "It will not create a new file with current lesson. "
//                    + " It will only save to the existing lesson. Click Save As instead.");
//            saveAlert.initOwner(App.primaryStage);
//            saveAlert.show();
//        }else{
//            saveCurrentLessonPlanToFile(App.getCurrentCourseFile());
//        }
//    }
//    @FXML
//    private void saveAsMenuAction(ActionEvent event) {
//        FileChooser fileChooser = new FileChooser();
//        fileChooser.setTitle(currentLessonPlan.getTitle());
//        FileChooser.ExtensionFilter filter = new FileChooser.ExtensionFilter("Lesson Plan(*.coursePlan)","*.coursePlan");
//        fileChooser.getExtensionFilters().add(filter);
//        Window mainWindow = displayLesson.getScene().getWindow();
//        File chosenFile = fileChooser.showSaveDialog(mainWindow);
//        saveCurrentLessonPlanToFile(chosenFile);
//    }
//
//    private void saveCurrentLessonPlanToFile(File chosenFile) {
//        if (chosenFile != null) {
//            try {
//                App.saveCurrentCourseToFile(chosenFile);
//            } catch (IOException e) {
//                new Alert(Alert.AlertType.ERROR, "Error saving lesson plan file: " + chosenFile).show();
//            }
//        }
//    }
//    @FXML
//    private void loadMenuAction() {
//        FileChooser fileChooser = new FileChooser();
//        fileChooser.setTitle("New File");
//        FileChooser.ExtensionFilter filter = new FileChooser.ExtensionFilter("Course Logs (*.coursePlan)", "*.coursePlan");
//        fileChooser.getExtensionFilters().add(filter);
//        Window mainWindow = displayLesson.getScene().getWindow();
//        File chosenFile = fileChooser.showOpenDialog(mainWindow);
//        if (chosenFile != null) {
//            try {
//                App.loadCurrentCourseFromFile(chosenFile);
//                displayLesson.getChildren().clear();
//                LessonPlan loadedLesson = App.getCurrentCourse().getCurrentLessonPlan();
//                lessonTitle.setText(loadedLesson.getTitle());
//
//                for(Card card: loadedLesson.getCopyOfLessonCards()){
//                    ImageView cardImageView = new ImageView(card.getImage());
//                    cardImageView.setFitWidth(1650/7);
//                    cardImageView.setFitHeight(1275/7);
//                    setMouseEvent(cardImageView, card, false);
//                    displayLesson.getChildren().add(cardImageView);
//                }
//                savedStatus = true;
//
//            } catch (IOException ex) {
//                new Alert(Alert.AlertType.ERROR, "Error loading movie log file: " + chosenFile).show();
//            }
//        }
//    }
}


//<MenuItem fx:id="saveButton" mnemonicParsing="false" onAction="#saveMenuAction" text="Save" />
//<MenuItem fx:id="saveAsButton" mnemonicParsing="false" onAction="#saveAsMenuAction" text="Save As" />
// <MenuItem mnemonicParsing="false" onAction="#loadMenuAction" text="Open" />
//<MenuItem mnemonicParsing="false" onAction="#shareMenuAction" text="Share" />
