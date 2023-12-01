package edu.augustana;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
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
    private LessonPlan currentLessonPlan = App.getCurrentCourse().getCurrentLessonPlan();
    @FXML
    private Label lessonTitle;

    private static final int CARDS_PER_PAGE = 9;

    @FXML
    void initialize() throws FileNotFoundException {
        back.setOnAction(event -> connectToPlanMakerPage());
        homeIcon.setImage(App.homeIcon());

        lessonTitle.setText(currentLessonPlan.getTitle());

        currentLessonPlan.displayCards(1650/8, 1275/8,displayLesson);

        print.setOnAction(event -> printContent(printCardsDisplay));

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
                    System.out.println("Display lesson get children"+displayLesson.getChildren().size());
                    int endIndex = Math.min(cardIndex + CARDS_PER_PAGE, displayLesson.getChildren().size());
                    boolean success = job.printPage(createPage(nodeToPrint, cardIndex, endIndex, job.getPrinter().getDefaultPageLayout().getPrintableHeight()));
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

    private Node createPage(Node nodeToPrint, int startIndex, int endIndex, double printableHeight) {
        VBox page = new VBox();
        page.setAlignment(Pos.CENTER);
        page.setSpacing(0);

        if (startIndex < displayLesson.getChildren().size()) {
            Label titleLabel = new Label(lessonTitle.getText());
            titleLabel.setPadding(new Insets(0));
            titleLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");
            page.getChildren().add(titleLabel);

            // Check and set padding and margin for TilePane
            TilePane tilePane = createTilePane(nodeToPrint, startIndex, endIndex, printableHeight - titleLabel.getHeight());
            tilePane.setPadding(new Insets(0));
            tilePane.setMargin(titleLabel, new Insets(0));

            page.getChildren().add(tilePane);
        }

        return page;
    }

    private TilePane createTilePane(Node nodeToPrint, int startIndex, int endIndex, double printableHeight) {
        TilePane tilePane = new TilePane();
        tilePane.setTileAlignment(Pos.CENTER);
        tilePane.setPrefColumns(3);
        tilePane.setPrefRows(3);
        tilePane.getChildren().addAll(displayLesson.getChildren().subList(startIndex, endIndex));

//        if (tilePane.getHeight() > printableHeight) {
//            tilePane.setPrefRows(tilePane.getChildren().size() / 3);
//        }

        return tilePane;
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
