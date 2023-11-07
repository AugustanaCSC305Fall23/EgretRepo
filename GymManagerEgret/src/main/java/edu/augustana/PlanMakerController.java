package edu.augustana;
import java.io.*;
import java.net.URL;
import java.util.*;

import edu.augustana.filters.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.control.ButtonType;
import javafx.print.PrinterJob;
import javafx.print.PrintQuality;
import javafx.print.PageLayout;
import javafx.scene.transform.Scale;


public class PlanMakerController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button print;
    @FXML
    private Button edit;

    @FXML
    private TextField codeSearchBox;

    @FXML
    private Button home;

    @FXML
    private Label label;

    private ObservableList<String> selectedImageReferences = FXCollections.observableArrayList();


    @FXML
    private TextArea textArea;

    @FXML
    private TextField titleSearchBox;

    @FXML
    private Button clearButton;


    @FXML
    private FlowPane cardFlowPane;

    @FXML
    private ScrollPane cardScrollPane;
    @FXML
    private ImageView cardImageView;

    private String enteredTitle;

    @FXML
    private TilePane displayLesson;

    private final ArrayList<Card> allCards = CardDatabase.allCards;

    @FXML
    private FlowPane cardImages;

    @FXML
    private ChoiceBox<String> categoryChoiceBox;

    @FXML
    private ChoiceBox<String> eventChoiceBox;

    @FXML
    private ChoiceBox<String> equipmentChoiceBox;

    private ArrayList<Image> lessonCards = new ArrayList<>();


    private Set<String> addedCardIDs = new HashSet<>();

    public PlanMakerController() {
    }


    @FXML
    void connectToHomePage() {
        App.switchToHomePageView();
    }

    @FXML
    void initialize() throws FileNotFoundException {

        BorderPane.setAlignment(home, Pos.TOP_LEFT);
        label.setVisible(true);
        textArea.setVisible(false);
        edit.setOnAction(event -> onEditButtonClick());
        initializeCardDisplay();
        //category filter
        initializeComboBoxes();
        //filterCardDisplay(filteredCards);
        print.setOnAction(event ->printContent(displayLesson));


    }


    private void showImagePopup(Image image, boolean addOrRemove) {
        Alert imageAlert = new Alert(AlertType.INFORMATION);
        imageAlert.initOwner(App.primaryStage);
        imageAlert.setHeaderText(null);
        imageAlert.setTitle("View Card");

        ImageView popupImageView = new ImageView(image);
        popupImageView.setFitWidth(1650/3);
        popupImageView.setFitHeight(1275/3);

        VBox contentVBox = new VBox(popupImageView);
        contentVBox.setAlignment(Pos.CENTER);
        contentVBox.setSpacing(10);

        imageAlert.getDialogPane().setContent(contentVBox);
        imageAlert.setGraphic(null);
        ButtonType addCardButtonType = new ButtonType("Add Card");
        ButtonType removeCardButtonType = new ButtonType("Remove Card");

        if(addOrRemove) {
            imageAlert.getButtonTypes().setAll(addCardButtonType, ButtonType.CANCEL);
        }else{
            imageAlert.getButtonTypes().setAll(removeCardButtonType, ButtonType.CANCEL);
        }

        addOrDeleteCards(image, imageAlert, addCardButtonType);
        imageAlert.showAndWait();


    }

    private void addOrDeleteCards(Image image, Alert imageAlert, ButtonType addCardButtonType){
        imageAlert.setResultConverter(buttonType -> {
            if (buttonType == addCardButtonType) {
                if (!lessonCards.contains(image)) {

                    ImageView cardImageView = new ImageView(image);
                    cardImageView.setFitWidth(1650/6.5);
                    cardImageView.setFitHeight(1275/6.5);
                    lessonCards.add(image);
                    displayLesson.getChildren().add(cardImageView);
                    cardImageView.setOnMouseClicked(event -> showImagePopup(image, false));
                }else{
                    Alert alreadyAddedAlert = new Alert(AlertType.INFORMATION);
                    alreadyAddedAlert.setHeaderText(null);
                    alreadyAddedAlert.setTitle("Add Card");
                    alreadyAddedAlert.setContentText("Already added image");
                    alreadyAddedAlert.initOwner(App.primaryStage);
                    alreadyAddedAlert.showAndWait();
                }
            } else {
                lessonCards.remove(image);
                displayLesson.getChildren().remove(cardImageView);
                // addedCardIDs.remove(image.toString());
               // System.out.println(addedCardIDs);
                System.out.println(lessonCards.toString());
            }
            return buttonType;
        });
    }

    private void printContent(TilePane nodeToPrint) {
        System.out.println("printContent called: " + nodeToPrint);
        PrinterJob job = PrinterJob.createPrinterJob();
        System.out.println("Job=" + job);
        if (job != null && job.showPrintDialog(nodeToPrint.getScene().getWindow())){
            boolean success = job.printPage(nodeToPrint);
            if (success) {
                job.endJob();
            }
        }
    }


    private void initializeComboBoxes(){
        ArrayList<String> category = new ArrayList<>();
        ArrayList<String> equipment = new ArrayList<>();
        ArrayList<String> event = new ArrayList<>();
        category.add("ALL");
        equipment.add("ALL");
        event.add("ALL");
        for(Card card : allCards){
            for(String item : card.getEquipment()){
                if(!equipment.contains(item)){
                    equipment.add(item);
                }
            }


            if(!event.contains(card.getEvent())) {
                event.add(card.getEvent());
            }
            if(!category.contains(card.getCategory())) {
                category.add(card.getCategory());
            }
        }
        categoryChoiceBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> { CardDatabase.addFilter(new CategoryFilter(categoryChoiceBox.getSelectionModel().getSelectedItem()));
            try {
                setCardDisplay(CardDatabase.filterCards());
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        });
        categoryChoiceBox.getItems().addAll(category);
        equipmentChoiceBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> { CardDatabase.addFilter(new EquipmentFilter(equipmentChoiceBox.getSelectionModel().getSelectedItem()));
            try {
                setCardDisplay(CardDatabase.filterCards());
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        });
        equipmentChoiceBox.getItems().addAll(equipment);
        eventChoiceBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> { CardDatabase.addFilter(new EventFilter(eventChoiceBox.getSelectionModel().getSelectedItem()));
            try {
                setCardDisplay(CardDatabase.filterCards());
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        });
        eventChoiceBox.getItems().addAll(event);
    }
    private void initializeCardDisplay() throws FileNotFoundException {
        //Loops through every card, turns them into an imageView, and then displays them.
        cardFlowPane.setPadding(new Insets(5,5,5,5));
        setCardDisplay(allCards);
    }


    public void setCardDisplay(ArrayList<Card> cardSelection) throws FileNotFoundException {
        cardFlowPane.getChildren().clear();
        for(Card card : cardSelection) {
            ImageView newCardView = new ImageView();
            Image cardImage = new Image(new FileInputStream("DEMO1ImagePack/" + card.getImage()));
            newCardView.setImage(cardImage);
            newCardView.setFitHeight(150);
            newCardView.setFitWidth(200);
            newCardView.setOnMouseClicked(event -> showImagePopup(cardImage, true));
            cardFlowPane.getChildren().add(newCardView);
            //System.out.println(card);
        }
    }

    @FXML
    private void resetFilters() throws FileNotFoundException {
        categoryChoiceBox.setValue("ALL");
        eventChoiceBox.setValue("ALL");
        equipmentChoiceBox.setValue("ALL");
        CardDatabase.clearFilters();
        setCardDisplay(allCards);
    }
    @FXML
    void onEditButtonClick() {
        label.setVisible(false);
        textArea.setVisible(true);
        textArea.setText(label.getText());
        textArea.requestFocus();
        edit.setDisable(true);
    }
    @FXML
    private void inputBasic() {resolveDifficultyButtons("B");}
    @FXML
    private void inputAdvanceBasic() {resolveDifficultyButtons("AB");}
    @FXML
    private void inputIntermediate() {resolveDifficultyButtons("I");}
    private void resolveDifficultyButtons(String level) {
        CardDatabase.addFilter(new DifficultyFilter(level));
        try {
            setCardDisplay(CardDatabase.filterCards());
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    @FXML
    private void inputMale() throws FileNotFoundException {resolveGenderButtons("M");}
    @FXML
    private void inputFemale() throws FileNotFoundException {resolveGenderButtons("F");}
    @FXML
    private void inputNeutral() throws FileNotFoundException {resolveGenderButtons("N");}
    private void resolveGenderButtons(String gender) throws FileNotFoundException {
        CardDatabase.addFilter(new GenderFilter(gender));
        try {
            setCardDisplay(CardDatabase.filterCards());
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    public void searchCode() throws FileNotFoundException {
        System.out.println(codeSearchBox.getText());
        CardDatabase.addFilter(new CodeFilter(codeSearchBox.getText().toUpperCase()));
        try {
            setCardDisplay(CardDatabase.filterCards());
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    public void searchTitle() throws FileNotFoundException {
        System.out.println(titleSearchBox.getText());
        CardDatabase.addFilter(new TitleFilter(titleSearchBox.getText().toLowerCase()));
        try {
            setCardDisplay(CardDatabase.filterCards());
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }


    @FXML
    void onEnterPressed(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            String editedText = textArea.getText();
            label.setText(editedText);
            enteredTitle = editedText;
            textArea.setVisible(false);
            label.setVisible(true);
            edit.setDisable(false);
        }
    }



           @FXML
          private void completeLessonPlan() {

           }
//        // Assuming that lessonCards contains the selected cards
//        LessonPlan current = Course.currentLessonPlan;
//        //for (ImageView cardImageView : lessonCards) {
//            // Create a new ImageView for each card
//            ImageView libraryCardImageView = new ImageView(cardImageView.getImage());
//
//            // Add the ImageView to the libraryFlowPane in your library view
//            displayLesson.getChildren().add(libraryCardImageView);
//            //current.addCard();
//        }
//
//        // After adding the cards to the library view, you can switch to the library view
//        App.switchToLibraryView();
    }






