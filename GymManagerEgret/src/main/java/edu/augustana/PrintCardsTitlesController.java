package edu.augustana;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.print.*;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;

import java.io.FileNotFoundException;

public class PrintCardsTitlesController {
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

    private TilePane eventTitleTilePane = new TilePane();

    private LessonPlan currentLessonPlan = App.getCurrentCourse().getCurrentLessonPlan();


    @FXML
    private void initialize() throws FileNotFoundException {
        back.setOnAction(event -> connectToPlanMakerPage());
        homeIcon.setImage(App.homeIcon());

        addCardTitlesToPageTabs();

        eventTitleTilePane.setOrientation(Orientation.VERTICAL);
        eventTitleTilePane.setAlignment(Pos.TOP_LEFT);

        print.setOnAction(event -> printContent(displayLesson));
    }

    private void addCardTitlesToPageTabs() {
        VBox vBox = new VBox();
        vBox.setAlignment(Pos.TOP_LEFT);
        HBox lessonTitleHBox = new HBox();
        String lessonTitleStr = currentLessonPlan.getTitle();
        Label lessonTitle = new Label("Lesson Title: " + lessonTitleStr);
        lessonTitle.setMaxHeight(10);
        lessonTitle.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-font: Arial; ");
        lessonTitleHBox.getChildren().add(lessonTitle);
        vBox.getChildren().add(lessonTitleHBox);

        for (Card card : currentLessonPlan.getCopyOfLessonCards()) {
            String cardTitle = card.getTitle();
            Label title = new Label();
            title.setText(cardTitle);
            title.setStyle("-fx-font-size: 15px; -fx-font-weight: bold; -fx-font: Arial; ");

            // Set the text alignment of the Label to TOP_LEFT
            title.setAlignment(Pos.TOP_LEFT);
            eventTitleTilePane.getChildren().add(title);
        }

        vBox.getChildren().add(eventTitleTilePane);
        eventTitleTilePane.setPrefRows(25);

        displayLesson.getChildren().add(vBox);
    }


    private void printContent(Node nodeToPrint) {
        System.out.println("printContent called: " + nodeToPrint);
        PrinterJob job = PrinterJob.createPrinterJob();
        System.out.println("Job=" + job);

        if (job != null) {
            PageLayout pageLayout = job.getPrinter().createPageLayout(Paper.A4, PageOrientation.LANDSCAPE, Printer.MarginType.HARDWARE_MINIMUM );
            job.getJobSettings().setPageLayout(pageLayout);

            if (job.showPrintDialog(nodeToPrint.getScene().getWindow())) {

                boolean success = job.printPage(nodeToPrint);
                System.out.println("Print success: " + success);

                job.endJob();
            }
        }
    }

    @FXML
    private void connectToPlanMakerPage() {
        App.switchToPlanMakerView();
    }
    @FXML
    private void connectToHomePage(){
        App.switchToHomePageView();
    }

}