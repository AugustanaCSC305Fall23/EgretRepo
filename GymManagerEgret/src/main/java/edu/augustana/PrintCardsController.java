package edu.augustana;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.print.*;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class PrintCardsController {
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
    private LessonPlan currentLessonPlan = App.getCurrentCourse().getCurrentLessonPlan();
    @FXML
    private TabPane pagesTabPane;
    private List<VBox> pageVBoxes = new ArrayList<>();

    @FXML
    void initialize() throws FileNotFoundException {
        back.setOnAction(event -> connectToPlanMakerPage());
        homeIcon.setImage(App.homeIcon());

        addCardsToPageTabs();

        print.setOnAction(event -> printContent(printCardsDisplay));

    }

    private void addCardsToPageTabs() {
        Set<String> eventNames = new HashSet<>();
        String lessonTitleStr = "Lesson Title: "+ currentLessonPlan.getTitle();
        for (Card card : currentLessonPlan.getCopyOfLessonCards()) {
            eventNames.add(card.getEvent());
        }
        pagesTabPane.getTabs().clear();
        int pageNum = 1;
        for (String eventNameStr : eventNames) {
            VBox pageVBox = new VBox();
            pageVBoxes.add(pageVBox);
            Tab tab = new Tab("Page " + pageNum + ": " +eventNameStr);
            tab.setContent(pageVBox);
            pagesTabPane.getTabs().add(tab);
            Label lessonTitle = new Label(lessonTitleStr);
        //    lessonTitle.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-font: Arial; ");
            pageVBox.getChildren().add(lessonTitle);
            Label eventName = new Label("Event Name: " +eventNameStr);
         //   eventName.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-font: Arial; ");
            pageVBox.getChildren().add(eventName);
            TilePane tilePane = new TilePane();
            tilePane.setPrefColumns(3);
            tilePane.setPrefRows(3);
            pageNum++;

            for (Card card : currentLessonPlan.getCopyOfLessonCards()) {
                if (card.getEvent().equals(eventNameStr)) {
                    ImageView newCardView = new ImageView();
                    Image cardImage = card.getZoomedImage();
                    newCardView.setImage(cardImage);
                    newCardView.setFitWidth(1650/6.8);
                    newCardView.setFitHeight(1275/7.4);
                    tilePane.getChildren().add(newCardView);
                }
            }
            tilePane.setVgap(12);
            tilePane.setHgap(12);

            pageVBox.setAlignment(Pos.TOP_CENTER);
            tilePane.setAlignment(Pos.TOP_CENTER);
            pageVBox.getChildren().add(tilePane);

        }

    }

    @FXML
    void printContent(Node nodeToPrint) {
        System.out.println("printContent called: " + nodeToPrint);
        PrinterJob job = PrinterJob.createPrinterJob();
        System.out.println("Job=" + job);

        if (job != null) {
            PageLayout pageLayout = job.getPrinter().createPageLayout(Paper.A4, PageOrientation.LANDSCAPE, Printer.MarginType.EQUAL);
            job.getJobSettings().setPageLayout(pageLayout);

            if (job.showPrintDialog(nodeToPrint.getScene().getWindow())) {
                int tabNum = 1;

                for (Tab tab : pagesTabPane.getTabs()) {
                    boolean success = job.printPage(tab.getContent());
                    System.out.println("Print success for Tab " + tabNum + ": " + success);

                    if (!success) {
                        break;
                    }

                    tabNum++;
                }

                job.endJob();
            }
        }
    }


    @FXML
    void connectToPlanMakerPage() {
        App.switchToPlanMakerView();
    }

    @FXML
    void connectToHomePage() {
        App.switchToHomePageView();
    }


}
