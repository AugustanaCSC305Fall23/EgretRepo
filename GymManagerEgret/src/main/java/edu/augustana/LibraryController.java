package edu.augustana;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Window;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;


public class LibraryController {

    @FXML
    private ImageView home;

    @FXML
    private ListView<LessonPlan> lessonList;

    private final LessonPlan currentLessonPlan;

    public LibraryController() {
        currentLessonPlan = App.getCurrentCourse().getCurrentLessonPlan();
    }

    @FXML
    void connectToHomePage() {
        App.switchToHomePageView();
    }


    public void initialize() {
        home.setImage(App.homeIcon());
        lessonList.getItems().addAll(App.getCurrentCourse().getLessonPlans());
    }

    @FXML
    private void addLessonPlan() throws IOException {
        LessonPlan newLessonPlan = new LessonPlan();
        App.switchToEditLessonPlan(newLessonPlan, true);
    }


    @FXML
    private void deleteLessonPlan(ActionEvent event) {
        // Get the selected game from the ListView
        LessonPlan selectedLessonPlan = lessonList.getSelectionModel().getSelectedItem();
        if(selectedLessonPlan != null){
            lessonList.getItems().remove(selectedLessonPlan);
            App.getCurrentCourse().removeLessonPlan(selectedLessonPlan);
        } else {
            new Alert(Alert.AlertType.WARNING, "Select a lesson plan to delete first!").show();
        }
    }

    @FXML
    void duplicateLessonPlan() {
        // Get the selected food item
        LessonPlan selectedLessonPlan = lessonList.getSelectionModel().getSelectedItem();
        if (selectedLessonPlan != null) {
            LessonPlan newLessonPlan = new LessonPlan(selectedLessonPlan);
            App.getCurrentCourse().addLessonPlan(newLessonPlan);
            lessonList.getItems().add(newLessonPlan);
        } else {
            new Alert(Alert.AlertType.WARNING, "Select a lesson plan to duplicate first!").show();
        }
    }

    @FXML
    private void removeAllLessonPlans(ActionEvent event) {
        if (!lessonList.getItems().isEmpty()) {
            Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to remove all lesson plans?");
            confirmation.initOwner(App.primaryStage);
            confirmation.setHeaderText(null);

            confirmation.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {
                    lessonList.getItems().clear();
                    App.getCurrentCourse().clearAllLessonPlans();
                }
            });
        } else {
            new Alert(Alert.AlertType.WARNING, "There are no lesson plans to remove!").show();
        }
    }

    @FXML
    private void openLessonPlan(ActionEvent event) throws IOException {
        LessonPlan selectedLessonPlan = lessonList.getSelectionModel().getSelectedItem();
        if (selectedLessonPlan != null) {
            App.switchToEditLessonPlan(selectedLessonPlan, false);
        } else {
            new Alert(Alert.AlertType.WARNING, "Select a lesson plan to open first!").show();
        }
    }

    @FXML
    private void menuActionOpen(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Course Library");
        FileChooser.ExtensionFilter filter = new FileChooser.ExtensionFilter("Course Library (*.courseLib)", "*.courseLib");
        fileChooser.getExtensionFilters().add(filter);
        Window mainWindow = lessonList.getScene().getWindow();
        File chosenFile = fileChooser.showOpenDialog(mainWindow);
        if (chosenFile != null) {
            try {
                App.loadCurrentCourseFromFile(chosenFile);
                lessonList.getItems().clear();
                Course loadedCourse = App.getCurrentCourse();
                lessonList.getItems().addAll(loadedCourse.getLessonPlans());
            } catch (IOException ex) {
                new Alert(Alert.AlertType.ERROR, "Error loading course library file: " + chosenFile).show();
            }
        }
    }

    @FXML
    private void menuActionSave(ActionEvent event) {
        if (App.getCurrentCourseFile() == null) {
            Alert saveAlert = new Alert(Alert.AlertType.INFORMATION, "It will not create a new file with current lesson. "
                    + " It will only save to the existing lesson. Click Save As instead.");
            saveAlert.initOwner(App.primaryStage);
            saveAlert.show();
        } else {
            saveCurrentMovieLogToFile(App.getCurrentCourseFile());
        }
    }

    @FXML
    private void menuActionSaveAs(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save Course Library");
        FileChooser.ExtensionFilter filter = new FileChooser.ExtensionFilter("Course Library (*.courseLib)", "*.courseLib");
        fileChooser.getExtensionFilters().add(filter);
        Window mainWindow = lessonList.getScene().getWindow();
        File chosenFile = fileChooser.showSaveDialog(mainWindow);
        saveCurrentMovieLogToFile(chosenFile);
    }

    private void saveCurrentMovieLogToFile(File chosenFile) {
        if (chosenFile != null) {
            try {
                App.saveCurrentCourseToFile(chosenFile);
            } catch (IOException e) {
                new Alert(Alert.AlertType.ERROR, "Error saving movie course library file: " + chosenFile).show();
            }
        }
    }


}


