package edu.augustana;

import com.opencsv.exceptions.CsvValidationException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * JavaFX App
 */
public class App extends Application {

    //1 lesson plan
    public static LessonPlan currentLessonPlan;

    public static File currentLessonPlanFile = null;
    private static Scene scene;

    public static final CardDatabase database = new CardDatabase();
    public static Stage primaryStage;

    public static void setPrimaryStage(Stage stage) {
        primaryStage = stage;
    }

    @Override
    public void start(Stage stage) throws IOException {
        scene = new Scene(loadFXML("HomePage"), 800, 650);
        primaryStage = stage;
        primaryStage.setScene(scene);
        primaryStage.show();
        primaryStage.setFullScreen(true);

    }

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) throws CsvValidationException, IOException {
        CardDatabase.addCardsFromCSV("DEMO1.csv");
        CardDatabase.addCardsFromCSV("DEMO2.csv");
        database.printCards();
        launch();
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

    public static void switchToLibraryView() {
        switchToView("LibraryPage.fxml");
    }

    public static void switchToPlanMakerView() {
        switchToView("PlanMakerPage.fxml");
    }

    public static void switchToHomePageView() {
        switchToView("HomePage.fxml");
    }

    public static void switchToCourseLibraryView() {
        //change back later
        switchToView("LibraryPage.fxml");
    }
    public static Image homeIcon(){
        Image homeIconImage = new Image("file:CardPhotos/Icons/home_icon.png");
        return homeIconImage;
    }

    public static LessonPlan getCurrentLessonLog() {
        return currentLessonPlan;
    }

    public static File getCurrentLessonFile() {
        return currentLessonPlanFile;
    }


    public static void saveCurrentLessonPlanToFile(File fileToSaveTo) throws IOException {
        // after this, File will contain the data from the LessonPlan object
        currentLessonPlan.saveToFile(fileToSaveTo);
        currentLessonPlanFile = fileToSaveTo;


    }


    public static void loadCurrentLessonPlanFromFile(File fileToLoadFrom) throws IOException {
        currentLessonPlan = LessonPlan.loadFromFile(fileToLoadFrom);
        currentLessonPlanFile = fileToLoadFrom;


    }


    public static void switchToPrintCardsView() {
        switchToView("PrintCardsPage.fxml");
    }
    public static void switchToPrintCardsTitles(){
        switchToView("PrintCardsTitles.fxml");
    }
}
