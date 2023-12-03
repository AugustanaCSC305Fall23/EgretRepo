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
    private Label lessonTitle;

    @FXML
    private TabPane pagesTabPane;


    private List<VBox> pageVBoxes = new ArrayList<>();

    @FXML
    void initialize() throws FileNotFoundException {
        back.setOnAction(event -> connectToPlanMakerPage());
        homeIcon.setImage(App.homeIcon());

 //       lessonTitle.setText(currentLessonPlan.getTitle());

  //      currentLessonPlan.displayCards(1650/8, 1275/8,displayLesson);

        print.setOnAction(event -> printContent(printCardsDisplay));

        addCardsToPageTabs();
    }

    private void addCardsToPageTabs() {
        Set<String> eventNames = new HashSet<>();
        for (Card card : currentLessonPlan.getCopyOfLessonCards()) {
            eventNames.add(card.getEvent());
        }
        pagesTabPane.getTabs().clear();
        int pageNum = 1;
        for (String eventName : eventNames) {
            VBox pageVBox = new VBox();
            pageVBoxes.add(pageVBox);
            Tab tab = new Tab("Page " + pageNum + ": " +eventName);
            tab.setContent(pageVBox);
            pagesTabPane.getTabs().add(tab);
            pageVBox.getChildren().add(new Label(eventName));
            pageVBox.setAlignment(Pos.TOP_LEFT);
            TilePane tilePane = new TilePane();
            pageVBox.getChildren().add(tilePane);
            pageNum++;

            for (Card card : currentLessonPlan.getCopyOfLessonCards()) {
                if (card.getEvent().equals(eventName)) {
                    ImageView newCardView = new ImageView();
                    Image cardImage = card.getZoomedImage();
                    newCardView.setImage(cardImage);
                    newCardView.setFitWidth(1650/8);
                    newCardView.setFitHeight(1275/8);
                    tilePane.getChildren().add(newCardView);
                }
            }
        }

//        int cardNum = displayLesson.getChildren().size();
//        while (!(cardNum==0)){
//            int tabNum = 1;
//            String tabName = "page"+ tabNum;
//            Tab tabName = new Tab();
//
//            if (cardNum>9){
//                cardNum-= 9;
//            }else{
//                cardNum-= cardNum;
//            }
//            tabNum++;
//
//        }

    }

    @FXML
    void printContent(Node nodeToPrint) {
        System.out.println("printContent called: " + nodeToPrint);
        PrinterJob job = PrinterJob.createPrinterJob();
        System.out.println("Job=" + job);

        if (job != null) {
            PageLayout pageLayout = job.getPrinter().createPageLayout(Paper.A4, PageOrientation.LANDSCAPE, Printer.MarginType.DEFAULT);
            job.getJobSettings().setPageLayout(pageLayout);

            if (job.showPrintDialog(nodeToPrint.getScene().getWindow())) {
                int tabNum = 1;

                for (Tab tab : pagesTabPane.getTabs()) {
                    // Print each tab content
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


//    private Node createPage(Node nodeToPrint, int startIndex, int endIndex, double printableHeight) {
//        VBox page = new VBox();
//        page.setAlignment(Pos.CENTER);
//        page.setSpacing(0);
//
//        if (startIndex < displayLesson.getChildren().size()) {
//            Label titleLabel = new Label(lessonTitle.getText());
//            titleLabel.setPadding(new Insets(0));
//            titleLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");
//            page.getChildren().add(titleLabel);
//
//            // Check and set padding and margin for TilePane
//            TilePane tilePane = createTilePane(nodeToPrint, startIndex, endIndex, printableHeight - titleLabel.getHeight());
//            tilePane.setPadding(new Insets(0));
//            tilePane.setMargin(titleLabel, new Insets(0));
//
//            page.getChildren().add(tilePane);
//        }
//
//        return page;
//    }
//
//    private TilePane createTilePane(Node nodeToPrint, int startIndex, int endIndex, double printableHeight) {
//        TilePane tilePane = new TilePane();
//        tilePane.setTileAlignment(Pos.CENTER);
//        tilePane.setPrefColumns(3);
//        tilePane.setPrefRows(3);
//        tilePane.getChildren().addAll(displayLesson.getChildren().subList(startIndex, endIndex));
//
////        if (tilePane.getHeight() > printableHeight) {
////            tilePane.setPrefRows(tilePane.getChildren().size() / 3);
////        }
//
//        return tilePane;
//    }
    @FXML
    void connectToPlanMakerPage() {
        App.switchToPlanMakerView();
    }

    @FXML
    void connectToHomePage() {
        App.switchToHomePageView();
    }


}
