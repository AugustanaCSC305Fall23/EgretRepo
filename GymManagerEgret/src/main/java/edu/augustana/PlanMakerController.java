package edu.augustana;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.Set;

import edu.augustana.filters.*;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
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


public class PlanMakerController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;


    @FXML
    private Button edit;

    @FXML
    private TextField codeSearchBox;

    @FXML
    private Button home;

    @FXML
    private Label label;

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
    private HBox displayLesson;

    private final ArrayList<Card> allCards = CardDatabase.allCards;

    @FXML
    private FlowPane cardImages;

    @FXML
    private ChoiceBox<String> categoryChoiceBox;

    @FXML
    private ChoiceBox<String> eventChoiceBox;

    @FXML
    private ChoiceBox<String> equipmentChoiceBox;

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

    }

    private void showImagePopup(Image image) {
        Alert imageAlert = new Alert(AlertType.INFORMATION);
        imageAlert.initOwner(App.primaryStage);
        imageAlert.setHeaderText(null);
        imageAlert.setTitle("Add Card");

        ImageView popupImageView = new ImageView(image);
        popupImageView.setFitWidth(400);
        popupImageView.setFitHeight(300);

        VBox contentVBox = new VBox(popupImageView);
        contentVBox.setAlignment(Pos.CENTER);
        contentVBox.setSpacing(10);

        imageAlert.getDialogPane().setContent(contentVBox);
        imageAlert.setGraphic(null);

        ButtonType addCardButtonType = new ButtonType("Add Card");

        imageAlert.getButtonTypes().setAll(addCardButtonType, ButtonType.CANCEL);

        imageAlert.setResultConverter(buttonType -> {
            if (buttonType == addCardButtonType) {
                String cardID = image.toString();
                if (!addedCardIDs.contains(cardID)) {
                    ImageView cardImageView = new ImageView(image);
                    cardImageView.setFitWidth(200);
                    cardImageView.setFitHeight(150);
                    displayLesson.getChildren().add(cardImageView);

                    // Add the card ID to the set of added card IDs
                    addedCardIDs.add(cardID);
                }else{
                    Alert alreadyAddedAlert = new Alert(AlertType.INFORMATION);
                    alreadyAddedAlert.setHeaderText(null);
                    alreadyAddedAlert.setTitle("Add Card");
                    alreadyAddedAlert.setContentText("Already added image");
                    alreadyAddedAlert.initOwner(App.primaryStage);
                    alreadyAddedAlert.showAndWait();
                }
            }
            return null;
        });


        imageAlert.showAndWait();



    }

    private void initializeComboBoxes(){
        ArrayList<String> category = new ArrayList<>();
        ArrayList<String> equipment = new ArrayList<>();
        ArrayList<String> event = new ArrayList<>();
        category.add(" ");
        equipment.add(" ");
        event.add(" ");
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
            newCardView.setOnMouseClicked(event -> showImagePopup(cardImage));
            cardFlowPane.getChildren().add(newCardView);
            System.out.println(card);
        }
    }

    @FXML
    private void resetFilters() throws FileNotFoundException {
        System.out.println("clear");
        System.out.println(allCards);
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


}
