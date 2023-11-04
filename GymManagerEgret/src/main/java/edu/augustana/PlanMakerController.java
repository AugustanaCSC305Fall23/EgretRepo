package edu.augustana;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.Set;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.Stage.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
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
    private Button home;

    @FXML
    private Label label;

    @FXML
    private TextArea textArea;

    @FXML
    private TextField textField;


    @FXML
    private FlowPane cardFlowPane;

    @FXML
    private ScrollPane cardScrollPane;
    @FXML
    private ImageView cardImageView;

    private String enteredTitle;

    @FXML
    private HBox displayLesson;

    private ArrayList<Card> allCards = CardDatabase.allCards;

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
            equipment.addAll(card.getEquipment());
            if(!event.contains(card.getEvent())) {
                event.add(card.getEvent());
            }
            if(!category.contains(card.getCategory())) {
                category.add(card.getCategory());
            }
        }


        categoryChoiceBox.getItems().addAll(category);
        equipmentChoiceBox.getItems().addAll(equipment);
        eventChoiceBox.getItems().addAll(event);
    }

    private void initializeCardDisplay() throws FileNotFoundException {
        //Loops through every card, turns them into an imageView, and then displays them.
        cardFlowPane.setPadding(new Insets(5,5,5,5));
        for(Card card : allCards) {
            ImageView newCardView = new ImageView();
            Image cardImage = new Image(new FileInputStream("DEMO1ImagePack/" + card.getImage()));
            newCardView.setImage(cardImage);
            newCardView.setFitHeight(150);
            newCardView.setFitWidth(200);
            newCardView.setOnMouseClicked(evt -> System.out.print(card.getCode()));
            cardFlowPane.getChildren().add(newCardView);
            newCardView.setOnMouseClicked(event -> showImagePopup(cardImage));
        }
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
