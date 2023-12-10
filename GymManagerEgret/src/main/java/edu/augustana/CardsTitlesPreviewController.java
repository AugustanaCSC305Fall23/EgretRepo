package edu.augustana;

import javafx.fxml.FXML;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.print.*;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;

import java.io.FileNotFoundException;

public class CardsTitlesPreviewController {
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

        print.setOnAction(event -> printContent(printCardsDisplay));

    }



    private void addCardTitlesToPageTabs() {
        VBox vBox = new VBox();
        vBox.setAlignment(Pos.TOP_LEFT);
        HBox lessonTitleHBox = new HBox();
        String lessonTitleStr = currentLessonPlan.getTitle();
        Label lessonTitle = new Label("Lesson Title: " + lessonTitleStr);
        lessonTitle.setMaxHeight(10);
        lessonTitle.setStyle("-fx-font-size: 17px; -fx-font-family: 'Arial';");
        lessonTitleHBox.getChildren().add(lessonTitle);
        vBox.getChildren().add(lessonTitleHBox);

        for (Card card : currentLessonPlan.getCopyOfLessonCards()) {
            String cardTitle = card.getTitle() + ", ("+ card.getCode()+")";
            Label title = new Label();
            title.setText(cardTitle);
            title.setStyle("-fx-font-size: 12px; -fx-font-family: 'Arial';");

            // Set the text alignment of the Label to TOP_LEFT
            title.setAlignment(Pos.TOP_LEFT);
            eventTitleTilePane.getChildren().add(title);
        }

        vBox.getChildren().add(eventTitleTilePane);
        eventTitleTilePane.setPrefRows(25);

        displayLesson.getChildren().add(vBox);
    }


    private void printContent(Node nodeToPrint) {
        // Create printer job
        PrinterJob job = PrinterJob.createPrinterJob();

        if (job != null) {
            // Set page layout
            PageLayout pageLayout = job.getPrinter().createPageLayout(Paper.A4, PageOrientation.LANDSCAPE, Printer.MarginType.DEFAULT);
            job.getJobSettings().setPageLayout(pageLayout);

            // Print each title separately
            if (job.showPrintDialog(nodeToPrint.getScene().getWindow())) {
                boolean success = job.printPage(nodeToPrint);

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