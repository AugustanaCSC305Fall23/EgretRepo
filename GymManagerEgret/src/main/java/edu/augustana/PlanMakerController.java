package edu.augustana;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;

public class PlanMakerController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button home;

    @FXML
    private BorderPane rootPane;

    @FXML
    private Label label;

    @FXML
    private TextArea textArea;

    @FXML
    private StackPane stackPane;


    @FXML
    void connectToHomePage() {
        App.switchToHomePageView();
    }




    void initialize() {
        BorderPane.setAlignment(home, Pos.TOP_LEFT);
        label.setVisible(true);
        textArea.setVisible(false);

    }


    @FXML
    void onLabelClick(MouseEvent event) {
        label.setVisible(false);
        textArea.setVisible(true);
        textArea.setText(label.getText());
        //textArea.requestFocus();
    }

    @FXML
    void onEnterPressed(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            String editedText = textArea.getText();
            label.setText(editedText);
            textArea.setVisible(false);
            label.setVisible(true);
        }
    }


}
