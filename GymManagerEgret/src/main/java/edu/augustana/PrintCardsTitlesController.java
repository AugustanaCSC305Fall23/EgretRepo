
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
    @FXML
    private Label lessonTitle;
    private LessonPlan currentLessonPlan = App.getCurrentCourse().getCurrentLessonPlan();


    @FXML
    void initialize() throws FileNotFoundException {
        displayLesson.setOrientation(Orientation.VERTICAL);
        back.setOnAction(event -> connectToPlanMakerPage());

        homeIcon.setImage(App.homeIcon());
        for (Card card : currentLessonPlan.getCopyOfLessonCards()) {
            String cardName = card.getTitle();
            Label title = new Label();
            title.setText(cardName);
            title.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: #216065; ");
            displayLesson.getChildren().add(title);
        }
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

                System.out.println("Display lesson get children"+displayLesson.getChildren().size());
                boolean success = job.printPage(createPage(nodeToPrint));
                System.out.println("Print success: " + success);

                job.endJob();
            }
        }
    }

    private Node createPage(Node nodeToPrint) {
        VBox page = new VBox();
        page.setAlignment(Pos.TOP_CENTER);
        page.setSpacing(0);


        Label titleLabel = new Label(lessonTitle.getText());
        titleLabel.setStyle("-fx-font-size: 30px; -fx-font-weight: bold;-fx-font: Arial;");
        page.getChildren().add(titleLabel);

        TilePane tilePane = new TilePane();
        tilePane.setOrientation(Orientation.VERTICAL);
        tilePane.setPrefHeight(800);
        for (Card card : currentLessonPlan.getCopyOfLessonCards()) {
            String cardName = card.getTitle();
            Label title = new Label();
            title.setText(cardName);
            tilePane.getChildren().add(title);
        }
        tilePane.setTileAlignment(Pos.TOP_LEFT);

        page.getChildren().add(tilePane);

        return page;
    }

    @FXML
    void connectToPlanMakerPage() {
        App.switchToPlanMakerView();
    }
    @FXML
    void connectToHomePage(){
        App.switchToHomePageView();
    }




}
