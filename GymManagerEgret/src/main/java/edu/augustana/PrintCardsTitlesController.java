
package edu.augustana;

        import javafx.fxml.FXML;
        import javafx.geometry.Orientation;
        import javafx.print.*;
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
    //    @FXML
//    private Button home;
    @FXML
    private ImageView homeIcon;
    @FXML
    private Button print;
    @FXML
    private Label lessonTitle;

    @FXML
    void initialize() throws FileNotFoundException {
        displayLesson.setOrientation(Orientation.VERTICAL);
        //displayLesson.setAlignment(Pos.CENTER);

        back.setOnAction(event -> connectToPlanMakerPage());

        LessonPlan currentLessonPlan = App.getCurrentCourse().getCurrentLessonPlan();
        lessonTitle.setText(currentLessonPlan.getTitle());
        homeIcon.setImage(App.homeIcon());
        for (Card card : currentLessonPlan.getCopyOfLessonCards()) {
            String cardName = new String();
            cardName = card.getTitle();
            Label title = new Label();
            title.setText(cardName);
            title.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: #216065;");
            displayLesson.getChildren().add(title);
        }
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

    private void printContent(VBox nodeToPrint) {
        System.out.println("printContent called: " + nodeToPrint);
        PrinterJob job = PrinterJob.createPrinterJob();
        System.out.println("Job=" + job);

        if (job != null) {
            PageLayout pageLayout = job.getPrinter().createPageLayout(Paper.A4, PageOrientation.LANDSCAPE, Printer.MarginType.DEFAULT);
            job.getJobSettings().setPageLayout(pageLayout);

            // Adjust the width and height of the TilePane to fit the landscape page
            nodeToPrint.setPrefWidth(pageLayout.getPrintableWidth());
            nodeToPrint.setPrefHeight(pageLayout.getPrintableHeight());

            if (job.showPrintDialog(nodeToPrint.getScene().getWindow())) {
                boolean success = job.printPage(nodeToPrint);
                if (success) {
                    job.endJob();
                }
            }
        }
    }


}
