package edu.augustana;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.HBox;



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
    private StackPane pictureStackPane;
    private String enteredTitle;


    @FXML
    void connectToHomePage() {
        App.switchToHomePageView();
    }


    void initialize() {
        titleStackPane.setVisible(true);
        codeStackPane.setVisible(false);
        genderHbox.setVisible(false);
        difficultyHbox.setVisible(false);
        equipmentHbox.setVisible(false);
        categoryHbox.setVisible(false);
        eventHbox.setVisible(false);
        clearHbox.setVisible(false);
        BorderPane.setAlignment(home, Pos.TOP_LEFT);
        label.setVisible(true);
        textArea.setVisible(false);
        edit.setOnAction(event -> onEditButtonClick());
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


    @FXML
    void onButtonClick(ActionEvent event) {
        if (codeStackPane.isVisible()) {
            // Filters are visible, hide them
            codeStackPane.setVisible(false);
            difficultyHbox.setVisible(false);
            genderHbox.setVisible(false);
            equipmentHbox.setVisible(false);
            categoryHbox.setVisible(false);
            eventHbox.setVisible(false);
            clearHbox.setVisible(false);
            pictureStackPane.setPrefHeight(Double.MAX_VALUE);

        } else {
            // Filters are hidden, show them
            codeStackPane.setVisible(true);
            difficultyHbox.setVisible(true);
            genderHbox.setVisible(true);
            equipmentHbox.setVisible(true);
            categoryHbox.setVisible(true);
            eventHbox.setVisible(true);
            clearHbox.setVisible(true);
            pictureStackPane.setPrefHeight(Region.USE_COMPUTED_SIZE);



        }
    }

}
