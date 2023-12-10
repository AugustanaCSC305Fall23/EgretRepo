package edu.augustana;


import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Window;
import java.util.prefs.Preferences;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
/**
 * Controller for the CourseView page
 */

public class CourseViewController {

    @FXML
    private ImageView home;

    @FXML
    private ListView<LessonPlan> lessonList;

    private final LessonPlan currentLessonPlan;

    private CourseUndoRedoHandler undoRedoHandler;

    public CourseViewController() {
        currentLessonPlan = App.getCurrentCourse().getCurrentLessonPlan();
    }

    /**
     * Switch from CourseView to HomePage
     */
    @FXML
    public void connectToHomePage() {
        App.switchToHomePageView();
    }

    /**
     * The home icon is set, undoRedo is recreated, and
     * the lesson in the LessonPlan is shown when the CourseView Page launches.
     */
    public void initialize() {
        home.setImage(App.homeIcon());
        undoRedoHandler = new CourseUndoRedoHandler(this);
        lessonList.getItems().addAll(App.getCurrentCourse().getLessonPlans());
    }

    @FXML
    private void addLessonPlan() throws IOException {
        LessonPlan newLessonPlan = new LessonPlan("Untitled");
        App.switchToEditLessonPlan(newLessonPlan, true);
        undoRedoHandler.saveState();
    }

    @FXML
    private void deleteLessonPlan(ActionEvent event) {
        if(App.getCurrentCourse().getLessonPlans().size()>1){
        LessonPlan selectedLessonPlan = lessonList.getSelectionModel().getSelectedItem();
        if(selectedLessonPlan != null){
            lessonList.getItems().remove(selectedLessonPlan);
            App.getCurrentCourse().removeLessonPlan(selectedLessonPlan);
            undoRedoHandler.saveState();
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Select a lesson plan to delete first!");
            DialogPane dialogPane = alert.getDialogPane();
            dialogPane.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
            alert.initOwner(App.primaryStage);
        }}else{
            Alert alert = new Alert(Alert.AlertType.WARNING, "There needs to be alteast a lesson in course!");
            DialogPane dialogPane = alert.getDialogPane();
            dialogPane.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
            alert.initOwner(App.primaryStage);
            alert.showAndWait();
        }
    }

    @FXML
    private void duplicateLessonPlan() {
        LessonPlan selectedLessonPlan = lessonList.getSelectionModel().getSelectedItem();
        if (selectedLessonPlan != null) {
            LessonPlan newLessonPlan = new LessonPlan(selectedLessonPlan);
            App.getCurrentCourse().addLessonPlan(newLessonPlan);
            lessonList.getItems().add(newLessonPlan);
            undoRedoHandler.saveState();
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Select a lesson to make a copy!");
            DialogPane dialogPane = alert.getDialogPane();
            dialogPane.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
            alert.initOwner(App.primaryStage);
            alert.showAndWait();
        }
    }

    @FXML
    private void removeAllLessonPlans(ActionEvent event) {
        if (!lessonList.getItems().isEmpty()) {
            Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION, "Do you want to proceed with deleting all lessons?");
            DialogPane dialogPane = confirmation.getDialogPane();
            dialogPane.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
            confirmation.initOwner(App.primaryStage);
            confirmation.setHeaderText(null);

            confirmation.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {
                    lessonList.getItems().clear();
                    App.getCurrentCourse().clearAllLessonPlans();
                    lessonList.getItems().addAll(App.getCurrentCourse().getLessonPlans());
                }
            });
            undoRedoHandler.saveState();
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING, "There are no lesson to remove!");
            DialogPane dialogPane = alert.getDialogPane();
            dialogPane.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
            alert.show();
        }
    }

    @FXML
    private void openLessonPlan(ActionEvent event) throws IOException {
        LessonPlan selectedLessonPlan = lessonList.getSelectionModel().getSelectedItem();
        if (selectedLessonPlan != null) {
            App.switchToEditLessonPlan(selectedLessonPlan, false);
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Select a lesson to open!");
            DialogPane dialogPane = alert.getDialogPane();
            dialogPane.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
            alert.initOwner(App.primaryStage);
            alert.showAndWait();
        }
    }

    @FXML
    private void menuActionLoad(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Course Library");
        FileChooser.ExtensionFilter filter = new FileChooser.ExtensionFilter("Course Library (*.course)", "*.course");
        fileChooser.getExtensionFilters().add(filter);
        Window mainWindow = lessonList.getScene().getWindow();
        File chosenFile = fileChooser.showOpenDialog(mainWindow);
        if (chosenFile != null) {
            try {
                App.loadCurrentCourseFromFile(chosenFile);
                lessonList.getItems().clear();
                Course loadedCourse = App.getCurrentCourse();
                lessonList.getItems().addAll(loadedCourse.getLessonPlans());
                undoRedoHandler.saveState();
            } catch (IOException ex) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Error loading course file: " + chosenFile);
                DialogPane dialogPane = alert.getDialogPane();
                dialogPane.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
                alert.initOwner(App.primaryStage);
                alert.showAndWait();
            }
        }
    }

    @FXML
    private void menuActionSave() {
        if (App.getCurrentCourseFile() == null) {
            menuActionSaveAs();
        } else {
            savecurrentCoursetofile(App.getCurrentCourseFile());
        }
    }

    @FXML
    private void menuActionSaveAs() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save Course");
        FileChooser.ExtensionFilter filter = new FileChooser.ExtensionFilter("Course (*.course)", "*.course");
        fileChooser.getExtensionFilters().add(filter);
        Window mainWindow = lessonList.getScene().getWindow();
        File chosenFile = fileChooser.showSaveDialog(mainWindow);
        savecurrentCoursetofile(chosenFile);
    }

    private void savecurrentCoursetofile(File chosenFile) {
        if (chosenFile != null) {
            try {
                App.saveCurrentCourseToFile(chosenFile);
            } catch (IOException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Error saving course file: " + chosenFile);
                DialogPane dialogPane = alert.getDialogPane();
                dialogPane.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
                alert.initOwner(App.primaryStage);
                alert.showAndWait();
            }
        }
    }


    @FXML
    private void showToolTips(){
        Alert alreadyAddedAlert = new Alert(Alert.AlertType.INFORMATION);
        DialogPane dialogPane = alreadyAddedAlert.getDialogPane();
        dialogPane.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
        alreadyAddedAlert.setHeaderText(null);
        alreadyAddedAlert.setTitle("Navigate");
        alreadyAddedAlert.setContentText("Click a lesson Plan and click 'open' to open the lesson plan" + "\n" +
                "Click a lesson Plan and click 'duplicate' to duplicate the lesson plan."+ "\n" +
                "Click a lesson Plan and click 'delete' to delete the lesson plan.");
        alreadyAddedAlert.initOwner(App.primaryStage);
        alreadyAddedAlert.showAndWait();
    }

    @FXML
    private void onEditMenuUndo() {undoRedoHandler.undo();}
    @FXML
    private void onEditMenuRedo() {undoRedoHandler.redo();}

    public CourseViewController.State createMemento() {
        return new CourseViewController.State();
    }

    public void restoreState(CourseViewController.State planMakerState) {
        lessonList.getItems().clear();
        planMakerState.restore();
        lessonList.getItems().addAll(App.getCurrentCourse().getLessonPlans());
    }
    @FXML
    private void exitPlatform() {
        Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to exit without saving?");
        DialogPane dialogPane = confirmation.getDialogPane();
        dialogPane.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
        confirmation.initOwner(App.primaryStage);
        confirmation.setHeaderText(null);

        ButtonType yesButton = new ButtonType("Yes", ButtonBar.ButtonData.YES);
        ButtonType noButton = new ButtonType("No", ButtonBar.ButtonData.NO);

        confirmation.getButtonTypes().setAll(yesButton, noButton);

        confirmation.showAndWait().ifPresent(response -> {
            if (response == yesButton) {
                Platform.exit();
            } else {
                menuActionSaveAs();
            }
        });
    }


    public class State {
        Course course;

        public State() {
            course = (Course) App.getCurrentCourse().clone();
        }

        public void restore() {
            App.setCurrentCourse((Course) course.clone());
        }

    }
}


