package edu.augustana;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
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


public class PlanMakerController {

    @FXML
    private ResourceBundle resources;



    @FXML
    private URL location;

    @FXML
    private HBox categoryHbox;

    @FXML
    private HBox clearHbox;

    @FXML
    private Label codeLabel;

    @FXML
    private StackPane codeStackPane;

    @FXML
    private Button collapseButton;

    @FXML
    private HBox difficultyHbox;

    @FXML
    private Button edit;

    @FXML
    private HBox equipmentHbox;

    @FXML
    private HBox eventHbox;

    @FXML
    private StackPane pictureStackPane;

    @FXML
    private AnchorPane cardAnchorPane;

    @FXML
    private HBox genderHbox;

    @FXML
    private Button home;

    @FXML
    private Label label;

    @FXML
    private TextArea textArea;

    @FXML
    private TextField textField;

    @FXML
    private Label titleLabel;

    @FXML
    private StackPane titleStackPane;

    @FXML
    private FlowPane cardFlowPane;

    @FXML
    private ScrollPane cardScrollPane;
    @FXML
    private ImageView cardImageView;

    private String enteredTitle;


    private ArrayList<Card> allCards = CardDatabase.allCards;

    @FXML
    private FlowPane cardImages;



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

    private void showImagePopup(Image image) {
        Alert imageAlert = new Alert(AlertType.INFORMATION);
        imageAlert.initOwner(App.primaryStage); // Set the owner to the primary stage
        imageAlert.setTitle("Image Pop-up");
        imageAlert.setHeaderText(null);

        // Create an ImageView to display the image
        ImageView popupImageView = new ImageView(image);
        popupImageView.setFitWidth(400); // Adjust the width as needed
        popupImageView.setFitHeight(300); // Adjust the height as needed

        // Set the image as the content of the dialog
        imageAlert.getDialogPane().setContent(popupImageView);

        imageAlert.showAndWait();
    }




    @FXML
    void onEditButtonClick() {
        label.setVisible(false);
        textArea.setVisible(true);
        textArea.setText(label.getText());
        textArea.requestFocus();
        edit.setDisable(true); // Disable the "edit" button while editing

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
    void onTitleLabelClick(MouseEvent event) {
        // Hide the label
        titleLabel.setVisible(false);
        // Make the TextField visible for user input
        textField.setVisible(true);
        //textField.requestFocus();
    }

    @FXML
    void onCodeLabelClick(MouseEvent event) {
        // Hide the label
        codeLabel.setVisible(false);
        // Make the TextField visible for user input
        textField.setVisible(true);
       // textField.requestFocus();
    }

    @FXML
    void onTitleLabelPress(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            // Hide the label
            titleLabel.setVisible(false);

            // Make the TextField visible
            textField.setVisible(true);

            // Remove focus from the TextField
            textField.getParent().requestFocus();

            // Save the entered title
            enteredTitle = textField.getText();
        }
    }

    @FXML
    void onCodeLabelPress(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            // Hide the label
            titleLabel.setVisible(false);

            // Make the TextField visible
            textField.setVisible(true);

            // Remove focus from the TextField
            textField.getParent().requestFocus();

            // Save the entered title
            enteredTitle = textField.getText();
        }
    }


}
