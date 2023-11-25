package edu.augustana;

import javafx.fxml.FXML;
import javafx.print.*;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;

import java.io.FileNotFoundException;

public class PrintCardsEquipmentController {
    @FXML
    VBox printCardsDisplay;
    @FXML
    TilePane displayLesson;
    @FXML
    private Button back;
    @FXML
    private ImageView homeIcon;
    @FXML
    private Button print;
    @FXML
    private Label lessonTitle;

    @FXML
    void initialize() throws FileNotFoundException {
        back.setOnAction(event -> connectToPlanMakerPage());
        homeIcon.setImage(App.homeIcon());

        LessonPlan currentLessonPlan = App.getCurrentCourse().getCurrentLessonPlan();

        lessonTitle.setText(currentLessonPlan.getTitle());
        //displayLesson.setPrefColumns(3);
        currentLessonPlan.displayCards(150,200,displayLesson);
        print.setOnAction(event -> printContent(printCardsDisplay));


    }

    @FXML
    void connectToPlanMakerPage() {
        App.switchToPlanMakerView();
    }
    @FXML
    void connectToHomePage(){
        App.switchToHomePageView();
    }

    private void printContent(Node nodeToPrint) {
        PrinterJob job = PrinterJob.createPrinterJob();

        if (job != null) {
            PageLayout pageLayout = job.getPrinter().createPageLayout(Paper.A4, PageOrientation.LANDSCAPE, Printer.MarginType.DEFAULT);
            job.getJobSettings().setPageLayout(pageLayout);

            if (job.showPrintDialog(nodeToPrint.getScene().getWindow())) {
                boolean success = job.printPage(nodeToPrint);
                if (success) {
                    job.endJob();
                }
            }
        }
    }


}
