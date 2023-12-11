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
import javafx.scene.image.ImageView;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;


import java.io.FileNotFoundException;
import java.util.*;


public class CodeTitlesPreviewController {
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


        addTitlesToPageTabs();


        print.setOnAction(event -> printContent(printCardsDisplay));


    }


    void addTitlesToPageTabs(){
        VBox pageVBox = new VBox();
        pageVBoxes.add(pageVBox);

        //Make a new tab for the coach notes
        Tab tab = new Tab("Card Titles and Codes");
        tab.setContent(pageVBox);
        pagesTabPane.getTabs().add(tab);

        Label lessonTitle = new Label("Lesson Title: " + currentLessonPlan.getTitle());
        lessonTitle.setMaxHeight(10);
        lessonTitle.setStyle("-fx-font-size: 17px; -fx-font-family: 'Arial';");
        pageVBox.getChildren().add(lessonTitle);

        TilePane tilePane = new TilePane();
        for (Card card : currentLessonPlan.getCopyOfLessonCards()) {
            String cardTitle = card.getTitle() + ", ("+ card.getCode()+")";
            Label title = new Label();
            title.setText(cardTitle);
            title.setStyle("-fx-font-size: 12px; -fx-font-family: 'Arial';");


            // Set the text alignment of the Label to TOP_LEFT
            title.setAlignment(Pos.TOP_LEFT);
            tilePane.getChildren().add(title);
        }

        tilePane.setOrientation(Orientation.VERTICAL);
        tilePane.setAlignment(Pos.TOP_LEFT);
        tilePane.setPrefRows(35);
        pageVBox.setAlignment(Pos.TOP_CENTER);
        pageVBox.getChildren().add(tilePane);
        for(Card card :currentLessonPlan.getCopyOfLessonCards()){
            if (!card.getCardNotes().isEmpty()){
                generateCoachNotes(2);
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
        PrinterJob job = PrinterJob.createPrinterJob();


        if (job != null) {
            PageLayout pageLayout = job.getPrinter().createPageLayout(Paper.A4, PageOrientation.LANDSCAPE, Printer.MarginType.EQUAL_OPPOSITES);
            job.getJobSettings().setPageLayout(pageLayout);


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
