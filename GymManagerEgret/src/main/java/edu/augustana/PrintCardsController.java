package edu.augustana;

import javafx.fxml.FXML;
import javafx.print.PrinterJob;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class PrintCardsController {
    @FXML
    VBox printCardsDisplay;
    @FXML
    TilePane displayLesson;
    @FXML
    private Button back;
    @FXML
    private Button print;
    @FXML
    private Label lessonTitle;

    @FXML
    void initialize() throws FileNotFoundException {
        back.setOnAction(event -> connectToPlanMakerPage());
        lessonTitle.setText(App.currentLessonPlan.getTitle());

        for (Card card : App.currentLessonPlan.getCopyOfLessonCards()) {
            ImageView newCardView = new ImageView();
            Image cardImage = card.getImage();
            newCardView.setImage(cardImage);
            newCardView.setFitHeight(150);
            newCardView.setFitWidth(200);
            displayLesson.getChildren().add(newCardView);
        }
        print.setOnAction(event -> printContent(printCardsDisplay));



    }

    @FXML
    void connectToPlanMakerPage() {
        App.switchToPlanMakerView();
    }

    private void printContent(VBox nodeToPrint) {
        System.out.println("printContent called: " + nodeToPrint);
        PrinterJob job = PrinterJob.createPrinterJob();
        System.out.println("Job=" + job);
        if (job != null && job.showPrintDialog(nodeToPrint.getScene().getWindow())) {
            boolean success = job.printPage(nodeToPrint);
            if (success) {
                job.endJob();
            }
        }
    }
}
