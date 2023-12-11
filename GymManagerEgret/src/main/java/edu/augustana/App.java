package edu.augustana;

import com.opencsv.exceptions.CsvValidationException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import java.io.File;
import java.io.IOException;

/**
 * JavaFX App where everything is displayed
 */
public class App extends Application {

    private static Course currentCourse = new Course();

    private static File currentCourseFile = null;
    private static Scene scene;

    public static final CardDatabase database = new CardDatabase();
    public static Stage primaryStage;

    @Override
    public void start(Stage stage) throws IOException {
        scene = new Scene(loadFXML("HomePage"), 800, 650);
        primaryStage = stage;
        primaryStage.setScene(scene);
        primaryStage.show();
        primaryStage.setFullScreen(true);
        scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());

    }

    private static Parent loadFXML(String fxml) throws IOException{
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    private static void switchToView(String fxmlFileName) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxmlFileName));
            scene.setRoot(fxmlLoader.load());
        } catch (IOException ex) {
            System.err.println("Can't find FXML file " + fxmlFileName);
            ex.printStackTrace();
        }
    }
    /**
     * Switch to PlanMakerPage
     */
    public static void switchToPlanMakerView() {
        switchToView("PlanMakerPage.fxml");
    }

    /**
     * Switch to HomePage
     */
    public static void switchToHomePageView() {
        switchToView("HomePage.fxml");
    }

    /**
     * Switch to CourseViewPage
     */
    public static void switchCourseView() {
        switchToView("CourseViewPage.fxml");
    }

    /**
     * Switch to PrintCardsPage
     */
    public static void switchToPrintCardsView() {
        switchToView("CardsPage.fxml");
    }

    /**
     * Switch to PrintCardsTitlesPage
     */
    public static void switchToPrintCardsTitles(){
        switchToView("CodeTitlesPage.fxml");
    }

    /**
     * Switch to PrintCardsEquipmentPage
     */
    public static void switchToPrintCardsEquipment(){
        switchToView("CardsEquipmentsPage.fxml");
    }

    public static Image homeIcon(){
        return new Image("file:GymManagerAssets/cardPhotos/Icons/white_house.png");
    }

    public static Image backgroundImage(){
        return new Image("file:GymManagerAssets/cardPhotos/Pictures/BGPic.JPG");
    }

    /**
     * @return - CurrentCourse
     */
    public static Course getCurrentCourse() {
        return currentCourse;
    }

    public static void setCurrentCourse(Course currentCourse) {
        App.currentCourse = currentCourse;
    }
    /**
     * @return - Current Course File
     */
    public static File getCurrentCourseFile() {
        return currentCourseFile;
    }
    /**
     * Saves the current course
     */
    public static void saveCurrentCourseToFile(File fileToSaveTo) throws IOException {
        currentCourse.saveToFile(fileToSaveTo);
        currentCourseFile = fileToSaveTo;
    }
    /**
     * Loads the current course
     */
    public static void loadCurrentCourseFromFile(File fileToLoadFrom) throws IOException {
        currentCourse = Course.loadFromFile(fileToLoadFrom);
        currentCourseFile = fileToLoadFrom;
    }
    /**
     * If there is no existing lessonPlan, it makes new one otherwise opens the current lessonPlan
     */
    public static void switchToEditLessonPlan(LessonPlan lessonPlanToEdit, boolean addingNew) throws IOException {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("PlanMakerPage.fxml"));
            Parent root = fxmlLoader.load();
            PlanMakerController controller = fxmlLoader.getController();
            controller.setEditLessonPlan(lessonPlanToEdit, addingNew);
            scene.setRoot(root);
        }catch (IOException e){
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) throws CsvValidationException, IOException {
        CardDatabase.addCardsFromCSV();
        database.printCards();
        launch();
    }
}
