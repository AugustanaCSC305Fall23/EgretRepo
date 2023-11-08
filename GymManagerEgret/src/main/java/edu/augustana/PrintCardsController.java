package edu.augustana;

import javafx.fxml.FXML;
import javafx.print.PrinterJob;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class PrintCardsController {
    @FXML
    TilePane displayLesson;
    @FXML
    private Button back;



    @FXML
    void initialize() throws FileNotFoundException {
        back.setOnAction(event -> connectToPlanMakerPage());
        PlanMakerController controller = PlanMakerController.getInstance();
        ArrayList<Card> lessonCards = LessonPlan.getInstance().getLessonCards();

        System.out.println(lessonCards.toString());
        for (Card card : lessonCards) {
            ImageView newCardView = new ImageView();
            Image cardImage = new Image(new FileInputStream("DEMO1ImagePack/" + card.getImage()));
            newCardView.setImage(cardImage);
            newCardView.setFitHeight(150);
            newCardView.setFitWidth(200);
            displayLesson.getChildren().add(newCardView);
        }
       //printContent(displayLesson);


    }

    @FXML
    void connectToPlanMakerPage() {
        App.switchToPlanMakerView();
    }
    private void printContent(TilePane nodeToPrint) {
        System.out.println("printContent called: " + nodeToPrint);
        PrinterJob job = PrinterJob.createPrinterJob();
        System.out.println("Job=" + job);
        if (job != null && job.showPrintDialog(nodeToPrint.getScene().getWindow())){
            boolean success = job.printPage(nodeToPrint);
            if (success) {
                job.endJob();
            }
        }
    }

}
