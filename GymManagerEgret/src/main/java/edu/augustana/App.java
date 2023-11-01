package edu.augustana;

import com.opencsv.exceptions.CsvValidationException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;

    private static final CardDatabase database = new CardDatabase();

    @Override
    public void start(Stage stage) throws IOException {
        scene = new Scene(loadFXML("HomePage"), 800, 650);
        stage.setScene(scene);
        stage.show();

    }

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) throws CsvValidationException, IOException {
        //database.addCardsFromCSV();
        /*filter.filterFloor();*/
        database.printCards();
        //flowController.initializeCards(allCards);

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
        switchToView("CourseLibraryPage.fxml");
    }



}