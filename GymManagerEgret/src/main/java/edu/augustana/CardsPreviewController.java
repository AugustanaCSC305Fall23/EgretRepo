package edu.augustana;

import javafx.fxml.FXML;
import javafx.geometry.Orientation;
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
import java.util.*;

public class CardsPreviewController {
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
    private void initialize() throws FileNotFoundException {
        back.setOnAction(event -> connectToPlanMakerPage());
        homeIcon.setImage(App.homeIcon());

        addCardsToPageTabs();

        print.setOnAction(event -> printContent(printCardsDisplay));

    }

    private void addCardsToPageTabs() {
        Set<String> eventNames = new HashSet<>();
        String lessonTitleStr = "Lesson Title: "+ currentLessonPlan.getTitle();
        displayLesson.setPrefColumns(3);
        displayLesson.setPrefRows(3);
        for (Card card : currentLessonPlan.getCopyOfLessonCards()) {
            eventNames.add(card.getEvent());
        }

        pagesTabPane.getTabs().clear();
        int pageNum = 1;

        for (String eventNameStr : eventNames) {
            VBox pageVBox = new VBox();
            pageVBoxes.add(pageVBox);

            //Make a new tab for each event in the set and add it to the Vbox
            Tab tab = new Tab("Page " + pageNum + ": " +eventNameStr);
            tab.setContent(pageVBox);
            pagesTabPane.getTabs().add(tab);

            //Add lesson title and event name
            Label lessonTitle = new Label(lessonTitleStr);
            lessonTitle.setMaxHeight(10);
            pageVBox.getChildren().add(lessonTitle);
            Label eventName = new Label("Event Name: " +eventNameStr);
            pageVBox.getChildren().add(eventName);

            //Add cards in the lesson in a tilepane
            TilePane tilePane = new TilePane();
            tilePane.setPrefColumns(3);
            tilePane.setPrefRows(3);
            pageNum++;

            for (Card card : currentLessonPlan.getCopyOfLessonCards()) {
                if (card.getEvent().equals(eventNameStr)) {
                    ImageView newCardView = new ImageView();
                    Image cardImage = card.getZoomedImage();
                    newCardView.setImage(cardImage);
                    newCardView.setFitWidth(1650/7.4);
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
        for(Card card :currentLessonPlan.getCopyOfLessonCards()){
            if (!card.getCardNotes().isEmpty()){
                generateCoachNotes(pageNum);
                break;
            }
        }

    }

    void generateCoachNotes(int pageNum){
        VBox pageVBox = new VBox();
        pageVBoxes.add(pageVBox);

        //Make a new tab for the coach notes
        Tab tab = new Tab("Page " + pageNum + ": Coach Notes");
        tab.setContent(pageVBox);
        pagesTabPane.getTabs().add(tab);

        Label lessonTitle = new Label("Lesson Title: " + currentLessonPlan.getTitle());
        lessonTitle.setStyle("-fx-font-size: 20px; -fx-font-family: 'Arial';");

        lessonTitle.setMaxHeight(10);
        pageVBox.getChildren().add(lessonTitle);

        TilePane coachNotesTilePane = new TilePane();
        for (Card card : currentLessonPlan.getCopyOfLessonCards()) {
            if (!Objects.equals(card.getCardNotes(), "")) {
                String cardName = card.getTitle();
                Label title = new Label();
                title.setText(cardName);
                title.setStyle("-fx-font-size: 17px; -fx-font-family: 'Arial';");
                title.setAlignment(Pos.TOP_LEFT);
                Label notesLabel = new Label();
                notesLabel.setText(card.getCardNotes());
                notesLabel.setWrapText(true);
                notesLabel.setStyle("-fx-font-size: 14px; -fx-font-family: 'Arial';");
                coachNotesTilePane.getChildren().add(title);
                coachNotesTilePane.getChildren().add(notesLabel);
            }
        }
        coachNotesTilePane.setVgap(12);
        coachNotesTilePane.setHgap(12);
        coachNotesTilePane.setOrientation(Orientation.VERTICAL);
        coachNotesTilePane.setAlignment(Pos.TOP_LEFT);
        coachNotesTilePane.setPrefRows(25);
        pageVBox.setAlignment(Pos.TOP_CENTER);
        pageVBox.getChildren().add(coachNotesTilePane);
    }

    private void printContent(Node nodeToPrint) {
        //Create printerjob
        PrinterJob job = PrinterJob.createPrinterJob();

        if (job != null) {
            //Set page layout
            PageLayout pageLayout = job.getPrinter().createPageLayout(Paper.A4, PageOrientation.LANDSCAPE, Printer.MarginType.EQUAL);
            job.getJobSettings().setPageLayout(pageLayout);

            //Print each tab
            if (job.showPrintDialog(nodeToPrint.getScene().getWindow())) {
                for (Tab tab : pagesTabPane.getTabs()) {
                    boolean success = job.printPage(tab.getContent());
                    if (!success) {
                        break;
                    }
                }

                job.endJob();
            }
        }
    }


    @FXML
    private void connectToPlanMakerPage() {
        App.switchToPlanMakerView();
    }

    @FXML
    private void connectToHomePage() {
        App.switchToHomePageView();
    }


}
