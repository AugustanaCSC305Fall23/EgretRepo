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
    @FXML
    private Label lessonTitle;

    private static final int CARDS_PER_PAGE = 9;

    @FXML
    void initialize() throws FileNotFoundException {
        back.setOnAction(event -> connectToPlanMakerPage());
        homeIcon.setImage(App.homeIcon());

        LessonPlan currentLessonPlan = App.getCurrentCourse().getCurrentLessonPlan();

        lessonTitle.setText(currentLessonPlan.getTitle());

        currentLessonPlan.displayCards(150,200,displayLesson);

        print.setOnAction(event -> printContent(printCardsDisplay));

    }

    @FXML
    void connectToPlanMakerPage() {
        App.switchToPlanMakerView();
    }

    @FXML
    void connectToHomePage() {
        App.switchToHomePageView();
    }

    private void printContent(Node nodeToPrint) {
        System.out.println("printContent called: " + nodeToPrint);
        PrinterJob job = PrinterJob.createPrinterJob();
        System.out.println("Job=" + job);

        if (job != null) {
            PageLayout pageLayout = job.getPrinter().createPageLayout(Paper.A4, PageOrientation.LANDSCAPE, Printer.MarginType.DEFAULT);
            job.getJobSettings().setPageLayout(pageLayout);

            if (job.showPrintDialog(nodeToPrint.getScene().getWindow())) {
                int cardIndex = 0;

                while (cardIndex < displayLesson.getChildren().size()) {
                    boolean success = job.printPage(createPage(nodeToPrint, cardIndex, job.getPrinter().getDefaultPageLayout().getPrintableHeight()));
                    System.out.println("Print success: " + success);

                    if (success) {
                        cardIndex += CARDS_PER_PAGE;
                    } else {
                        break;
                    }
                }

                job.endJob();
            }
        }
    }

    private Node createPage(Node nodeToPrint, int startIndex, double printableHeight) {
        VBox page = new VBox();

        if (startIndex < displayLesson.getChildren().size()) {
            Label titleLabel = new Label(lessonTitle.getText());
           // titleLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");
            page.getChildren().add(titleLabel);
            TilePane tilePane = createTilePane(nodeToPrint, startIndex, printableHeight - titleLabel.getHeight());
            page.getChildren().add(tilePane);
        }

        return page;
    }

    private TilePane createTilePane(Node nodeToPrint, int startIndex, double printableHeight) {
        TilePane tilePane = new TilePane();
        tilePane.setPrefColumns(3);
        tilePane.setPrefRows(3);

        int endIndex = Math.min(startIndex + CARDS_PER_PAGE, displayLesson.getChildren().size());
        tilePane.getChildren().addAll(displayLesson.getChildren().subList(startIndex, endIndex));

        if (tilePane.getHeight() > printableHeight) {
            tilePane.setPrefRows(tilePane.getChildren().size() / 3);
        }

        return tilePane;
    }

}
