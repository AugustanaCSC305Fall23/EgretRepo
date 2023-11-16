package edu.augustana;

import java.io.*;
import java.net.URL;
import java.util.*;
import edu.augustana.filters.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.print.*;
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
import javafx.scene.input.MouseButton;
import javafx.scene.layout.*;
import javafx.scene.control.ButtonType;
import javafx.stage.FileChooser;
import javafx.stage.Window;
import org.controlsfx.control.ToggleSwitch;


public class PlanMakerController {


    @FXML
    private Button print;



    @FXML
    private Button edit;

    @FXML
    private TextField codeSearchBox;



    @FXML
    private Label lessonTitle;

    private ObservableList<String> selectedImageReferences = FXCollections.observableArrayList();

    @FXML
    private TextArea lessonTitletextArea;

    @FXML
    private TextField titleSearchBox;

    @FXML
    private FlowPane cardFlowPane;

    private String enteredTitle;

    @FXML
    private TilePane displayLesson;

    private final ArrayList<Card> allCards = CardDatabase.allCards;
    @FXML
    private FlowPane cardImages;


    @FXML
    private MenuItem saveButton;

    @FXML
    private ImageView home;


    @FXML
    private ChoiceBox<String> categoryChoiceBox;

    @FXML
    private ChoiceBox<String> eventChoiceBox;

    @FXML
    private ChoiceBox<String> equipmentChoiceBox;


    @FXML
    private ToggleSwitch favoriteSwitch;

    private boolean savedStatus = false;

    FavoritesManager favoritesManager = FavoritesManager.getFavoritesManager();


    public PlanMakerController() {
    }


    @FXML
    void connectToHomePage() {
        App.switchToHomePageView();
    }

    @FXML
    void initialize() throws FileNotFoundException {

        home.setImage(App.homeIcon());
        lessonTitle.setVisible(true);
        lessonTitletextArea.setVisible(false);
        edit.setOnAction(event -> onEditButtonClick());
        edit.setOnAction(event -> onDoubleClick());
        setCardDisplay(allCards);
        initializeComboBoxes();

        App.currentLessonPlan = new LessonPlan(lessonTitle.getText());
        print.setOnAction(event ->printOptions());



    }


    private void showImagePopup(Card card, boolean addOrRemove) {
        Alert imageAlert = new Alert(AlertType.INFORMATION);
        imageAlert.initOwner(App.primaryStage);
        imageAlert.setHeaderText(null);
        imageAlert.setTitle("View Card");

        ImageView popupImageView = new ImageView(card.getImage());
        popupImageView.setFitWidth(1650/3);
        popupImageView.setFitHeight(1275/3);

        VBox contentVBox = new VBox(popupImageView);
        contentVBox.setAlignment(Pos.CENTER);
        contentVBox.setSpacing(10);

        imageAlert.getDialogPane().setContent(contentVBox);
        imageAlert.setGraphic(null);
        String placeButtonText;
        if(addOrRemove){
            placeButtonText = "Add Card";
        } else {
            placeButtonText = "Remove Card";
        }
        String favoriteButtonText;
        if (card.getFavoriteStatus()){
            favoriteButtonText = "Remove from Favorites";
        } else {
            favoriteButtonText = "Add to Favorites";
        }
        ButtonType placeCardButtonType = new ButtonType(placeButtonText);
        ButtonType toggleFavoriteButton = new ButtonType(favoriteButtonText);
        imageAlert.getButtonTypes().setAll(placeCardButtonType, toggleFavoriteButton, ButtonType.CANCEL);

        imageAlert.setResultConverter(buttonType -> {
            if (buttonType == placeCardButtonType) {
                if(addOrRemove){
                    imageAlert.close();
                    addCardToPlan(card);
                } else {
                    imageAlert.close();
                    removeCardFromPlan(card);
                }
            } else if(buttonType == toggleFavoriteButton){
                if(card.getFavoriteStatus()){
                    favoritesManager.removeFromFavorites(card.getCode());

                } else {
                    favoritesManager.saveToFavorites(card.getCode());
                }
                card.toggleFavorite();


            }
            return buttonType;
        });
        imageAlert.show();

    }
    private void setMouseEvent(ImageView cardPane, Card card, boolean addOrRemove){
        cardPane.setOnMouseClicked(event -> {
            if(event.getButton().equals(MouseButton.PRIMARY)) {
                showImagePopup(card,addOrRemove);
            }else if(event.getButton().equals(MouseButton.SECONDARY)){
                if(addOrRemove){
                    addCardToPlan(card);
                } else{
                    removeCardFromPlan(card);
                }
            }
        });
    }
    private void addCardToPlan(Card card){
        if (!App.currentLessonPlan.containsCard(card)) {
            ImageView cardImageView = new ImageView(card.getImage());
            cardImageView.setFitWidth(1650/6.5);
            cardImageView.setFitHeight(1275/6.5);
            displayLesson.getChildren().add(cardImageView);
            setMouseEvent(cardImageView, card, false);
            App.currentLessonPlan.addCard(card);
        }else{
            System.out.println("cardExists");
            Alert alreadyAddedAlert = new Alert(AlertType.INFORMATION);
            alreadyAddedAlert.setHeaderText(null);
            alreadyAddedAlert.setTitle("Add Card");
            alreadyAddedAlert.setContentText("Already added image");
            alreadyAddedAlert.initOwner(App.primaryStage);
            alreadyAddedAlert.showAndWait();

        }
    }

    private void removeCardFromPlan(Card card){
        App.currentLessonPlan.removeCard(card);
        try {
            updateLessonDisplay(App.currentLessonPlan.getCopyOfLessonCards());
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateLessonDisplay(ArrayList<Card> lessonCards) throws FileNotFoundException {
        displayLesson.getChildren().clear();
        for (Card card : lessonCards) {
            System.out.println(card);
            ImageView cardImageView = new ImageView(card.getImage());
            cardImageView.setFitWidth(1650 / 6.5);
            cardImageView.setFitHeight(1275 / 6.5);
            displayLesson.getChildren().add(cardImageView);
            setMouseEvent(cardImageView, card, false);
        }
    }


    private void printOptions(){
        System.out.println(App.currentLessonPlan.getTitle());
        Alert imageAlert = new Alert(AlertType.INFORMATION);
        imageAlert.initOwner(App.primaryStage);
        imageAlert.setHeaderText(null);
        imageAlert.setTitle("Print options");

        ButtonType printCards = new ButtonType("Print Cards");
        ButtonType printCardsTitles = new ButtonType("Print Cards Titles");
        ButtonType printCardsEquipment = new ButtonType("Print Cards with Equipment");

        imageAlert.setGraphic(null);
        imageAlert.getButtonTypes().setAll(printCards, printCardsTitles, printCardsEquipment, ButtonType.CANCEL);

        imageAlert.setResultConverter(buttonType -> {
            if (buttonType == printCards) {
                App.switchToPrintCardsView();
            }
            if (buttonType == printCardsTitles){
                App.switchToPrintCardsTitles();
            }
            if (buttonType == printCardsEquipment){
                App.switchToPrintCardsEquipment();
            }
            return buttonType;
        });
        imageAlert.showAndWait();

    }

    public void setCardDisplay(ArrayList<Card> cardSelection) throws FileNotFoundException {
        cardFlowPane.getChildren().clear();
        for(Card card : cardSelection) {
            ImageView newCardView = new ImageView();
            Image cardImage = card.getImage();
            newCardView.setImage(cardImage);
            newCardView.setFitHeight(150);
            newCardView.setFitWidth(200);
            setMouseEvent(newCardView, card, true);
            cardFlowPane.getChildren().add(newCardView);

        }
    }



    public ArrayList<Card> getLessonCards() {
        return App.currentLessonPlan.getCopyOfLessonCards();
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




    @FXML
    void onEditButtonClick() {
        lessonTitle.setVisible(false);
        lessonTitletextArea.setVisible(true);
        lessonTitletextArea.setText(lessonTitle.getText());
        lessonTitletextArea.requestFocus();
        edit.setDisable(true);
    }

    @FXML
    void onDoubleClick() {
        lessonTitle.setVisible(false);
        lessonTitletextArea.setVisible(true);
        lessonTitletextArea.setText(lessonTitle.getText());
        lessonTitletextArea.requestFocus();
        edit.setDisable(true);
    }
    @FXML
    void onEnterPressed(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            String editedText = lessonTitletextArea.getText();
            lessonTitle.setText(editedText);
            enteredTitle = editedText;
            lessonTitletextArea.setVisible(false);
            //remove
            App.currentLessonPlan.setTitle(enteredTitle);
            lessonTitle.setVisible(true);
            edit.setDisable(false);
            App.currentLessonPlan.setTitle(editedText);
        }
    }
    @FXML
    private void inputBasic() {resolveDifficultyButtons("B");}
    @FXML
    private void inputAdvanceBasic() {resolveDifficultyButtons("AB");}
    @FXML
    private void inputIntermediate() {resolveDifficultyButtons("I");}
    @FXML
    private void inputALLDifficulty() {resolveDifficultyButtons("ALL");}
    private void resolveDifficultyButtons(String level) {
        CardDatabase.addFilter(new DifficultyFilter(level));
        try {
            setCardDisplay(CardDatabase.filterCards());
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    @FXML
    private void inputMale() {resolveGenderButtons("M");}
    @FXML
    private void inputFemale() {resolveGenderButtons("F");}
    @FXML
    private void inputNeutral() {resolveGenderButtons("N");}
    @FXML
    private void inputALLGender() {resolveGenderButtons("ALL");}
    private void resolveGenderButtons(String gender) {
        CardDatabase.addFilter(new GenderFilter(gender));
        try {
            setCardDisplay(CardDatabase.filterCards());
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    @FXML
    private void searchFavorites() {
        if (favoriteSwitch.isSelected()){
            CardDatabase.addFilter(new FavoriteFilter());
        } else {
            CardDatabase.removeFilterType(new FavoriteFilter());
        }
        try {
            setCardDisplay(CardDatabase.filterCards());
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    public void searchCode() {
        CardDatabase.addFilter(new CodeFilter(codeSearchBox.getText().toUpperCase()));
        try {
            setCardDisplay(CardDatabase.filterCards());
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    public void searchTitle() {
        System.out.println(titleSearchBox.getText());
        CardDatabase.addFilter(new TitleFilter(titleSearchBox.getText().toLowerCase()));
        try {
            setCardDisplay(CardDatabase.filterCards());
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
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
      private void saveAsAction(ActionEvent event) {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle(App.currentLessonPlan.getTitle());
            FileChooser.ExtensionFilter filter = new FileChooser.ExtensionFilter("Lesson Plan(*.courselessonplan)","*.courselessonplan");
            fileChooser.getExtensionFilters().add(filter);
            Window mainWindow = displayLesson.getScene().getWindow();
            File chosenFile = fileChooser.showSaveDialog(mainWindow);
            saveCurrentLessonPlanToFile(chosenFile);
           }

    private void saveCurrentLessonPlanToFile(File chosenFile) {
        if (chosenFile != null) {
            try {
                App.saveCurrentLessonPlanToFile(chosenFile);
                savedStatus = true;
            } catch (IOException e) {
                new Alert(Alert.AlertType.ERROR, "Error saving lesson plan file: " + chosenFile).show();
            }
        }
    }


@FXML
private void loadAction() {
    FileChooser fileChooser = new FileChooser();
    fileChooser.setTitle("New File");
    FileChooser.ExtensionFilter filter = new FileChooser.ExtensionFilter("Course Logs (*.courselessonplan)", "*.courselessonplan");
    fileChooser.getExtensionFilters().add(filter);
    Window mainWindow = displayLesson.getScene().getWindow();
    File chosenFile = fileChooser.showOpenDialog(mainWindow);
    if (chosenFile != null) {
        try {
            App.loadCurrentLessonPlanFromFile(chosenFile);
            displayLesson.getChildren().clear();
            lessonTitle.setText(App.currentLessonPlan.getTitle());
            LessonPlan loadedLesson = App.getCurrentLessonLog();

            for(Card card: loadedLesson.getCopyOfLessonCards()){
                ImageView cardImageView = new ImageView(card.getImage());
                cardImageView.setFitWidth(1650/6.5);
                cardImageView.setFitHeight(1275/6.5);
                setMouseEvent(cardImageView, card, false);
                displayLesson.getChildren().add(cardImageView);
            }
            savedStatus = true;

        } catch (IOException ex) {
            new Alert(Alert.AlertType.ERROR, "Error loading movie log file: " + chosenFile).show();
        }
    }
}


}


