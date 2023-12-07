
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
    private LessonPlan currentLessonPlan = App.getCurrentCourse().getCurrentLessonPlan();


    @FXML
    void initialize() throws FileNotFoundException {
        //Adding action to buttons
        back.setOnAction(event -> connectToPlanMakerPage());
        homeIcon.setImage(App.homeIcon());

        //Adds card titles in tabs
        addCardTitlesToPageTabs();

        //Prints each tab in separate pages in one pdf
        print.setOnAction(event -> printContent(displayLesson));

        //Sets orientation
        displayLesson.setOrientation(Orientation.VERTICAL);

    }

    private void addCardTitlesToPageTabs() {
        //
        String lessonTitleStr = currentLessonPlan.getTitle();
        Label lessonTitle = new Label("Lesson Title: "+lessonTitleStr);
        lessonTitle.setMaxHeight(10);
        lessonTitle.setStyle("-fx-font-size: 25px; -fx-font-weight: bold; -fx-font: Arial; ");
        displayLesson.getChildren().add(lessonTitle);

        for (Card card : currentLessonPlan.getCopyOfLessonCards()) {
            String cardName = card.getTitle();
            Label title = new Label();
            title.setText(cardName);
            title.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-font: Arial; ");
            displayLesson.getChildren().add(title);
        }
    }

    private void printContent(Node nodeToPrint) {
        System.out.println("printContent called: " + nodeToPrint);
        PrinterJob job = PrinterJob.createPrinterJob();
        System.out.println("Job=" + job);

        if (job != null) {
            PageLayout pageLayout = job.getPrinter().createPageLayout(Paper.A4, PageOrientation.LANDSCAPE, Printer.MarginType.EQUAL);
            job.getJobSettings().setPageLayout(pageLayout);

            if (job.showPrintDialog(nodeToPrint.getScene().getWindow())) {

                System.out.println("Display lesson get children"+displayLesson.getChildren().size());
                boolean success = job.printPage(displayLesson);
                System.out.println("Print success: " + success);

                job.endJob();
            }
        }
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
