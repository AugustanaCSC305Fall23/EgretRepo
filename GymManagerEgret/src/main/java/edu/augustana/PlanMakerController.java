package edu.augustana;

import java.io.*;
import java.util.*;
import edu.augustana.filters.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.geometry.Pos;
import javafx.scene.control.*;
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

    private final ArrayList<Card> allCards = CardDatabase.allCards;
    private LessonPlan currentLessonPlan;
    private String enteredTitle;
    private boolean savedStatus = false;

    @FXML
    private MenuItem print;

    @FXML
    private MenuItem edit;

    @FXML
    private TextField codeSearchBox;

    @FXML
    private Label lessonTitle;

    @FXML
    private TextArea lessonTitleTextArea;

    @FXML
    private TextField titleSearchBox;

    @FXML
    private FlowPane cardFlowPane;

    @FXML
    private TilePane displayLesson;

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

    FavoritesManager favoritesManager = FavoritesManager.getFavoritesManager();

    public PlanMakerController() {
        currentLessonPlan = App.getCurrentCourse().getCurrentLessonPlan();
    }

    @FXML
    void connectToHomePage() {
        App.switchToHomePageView();
    }

    @FXML
    void initialize() throws FileNotFoundException {
        home.setImage(App.homeIcon());
        lessonTitle.setVisible(true);
        lessonTitleTextArea.setVisible(false);
        edit.setOnAction(event -> {
            onEditButtonClick();
            onTitleClick();
        });
        setCardDisplay(allCards);
        initializeComboBoxes();
        if(!currentLessonPlan.getCopyOfLessonCards().isEmpty()){
            lessonTitle.setText(currentLessonPlan.getTitle());
            for(Card card: currentLessonPlan.getCopyOfLessonCards()){
                ImageView cardImageView = new ImageView(card.getImage());
                cardImageView.setFitWidth(1650/7);
                cardImageView.setFitHeight(1275/7);
                setMouseEvent(cardImageView, card, false);
                displayLesson.getChildren().add(cardImageView);
            }
        }
        currentLessonPlan.setTitle(lessonTitle.getText());
        print.setOnAction(event ->printOptions());
    }


    private void showImagePopup(Card card, boolean addOrRemove) {
        Alert imageAlert = new Alert(AlertType.INFORMATION);
        imageAlert.initOwner(App.primaryStage);
        imageAlert.setHeaderText(null);
        imageAlert.setTitle("View Card");

        ImageView popupImageView = new ImageView(card.getZoomedImage());
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
                try {
                    setCardDisplay(CardDatabase.filterCards());
                } catch (FileNotFoundException e) {
                    throw new RuntimeException(e);
                }
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
        if (!currentLessonPlan.containsCard(card)) {
            ImageView cardImageView = new ImageView(card.getImage());
            cardImageView.setFitWidth(1650/7);
            cardImageView.setFitHeight(1275/7);

            displayLesson.getChildren().add(cardImageView);
            setMouseEvent(cardImageView, card, false);
            currentLessonPlan.addCard(card);
        }else{
            Alert alreadyAddedAlert = new Alert(AlertType.INFORMATION);
            alreadyAddedAlert.setHeaderText(null);
            alreadyAddedAlert.setTitle(null);
            alreadyAddedAlert.setContentText("Already added image");
            alreadyAddedAlert.initOwner(App.primaryStage);
            alreadyAddedAlert.showAndWait();
        }
    }

    private void removeCardFromPlan(Card card){
        currentLessonPlan.removeCard(card);
        try {
            updateLessonDisplay(currentLessonPlan.getCopyOfLessonCards());
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
        lessonTitleTextArea.setVisible(true);
        lessonTitleTextArea.setText(lessonTitle.getText());
        lessonTitleTextArea.requestFocus();
        edit.setDisable(true);
    }

    @FXML
    void onTitleClick() {
        lessonTitle.setVisible(false);
        lessonTitleTextArea.setVisible(true);
        lessonTitleTextArea.setText(lessonTitle.getText());
        lessonTitleTextArea.requestFocus();
        edit.setDisable(true);
    }
    @FXML
    void onEnterPressed(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            String editedText = lessonTitleTextArea.getText();
            lessonTitle.setText(editedText);
            enteredTitle = editedText;
            lessonTitleTextArea.setVisible(false);
            currentLessonPlan.setTitle(enteredTitle);
            lessonTitle.setVisible(true);
            edit.setDisable(false);
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
        favoriteSwitch.setSelected(false);
    }

    //Saving the lesson Plan
    @FXML
    private void saveMenuAction(ActionEvent event) {
        if(App.getCurrentCourseFile() == null){
            Alert saveAlert = new Alert(AlertType.INFORMATION, "It will not create a new file with current lesson. "
                    + " It will only save to the existing lesson. Click Save As instead.");
            saveAlert.initOwner(App.primaryStage);
            saveAlert.show();
        }else{
            saveCurrentLessonPlanToFile(App.getCurrentCourseFile());
        }
    }
    @FXML
    private void saveAsMenuAction(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle(currentLessonPlan.getTitle());
        FileChooser.ExtensionFilter filter = new FileChooser.ExtensionFilter("Lesson Plan(*.coursePlan)","*.coursePlan");
        fileChooser.getExtensionFilters().add(filter);
        Window mainWindow = displayLesson.getScene().getWindow();
        File chosenFile = fileChooser.showSaveDialog(mainWindow);
        saveCurrentLessonPlanToFile(chosenFile);
    }

    private void saveCurrentLessonPlanToFile(File chosenFile) {
        if (chosenFile != null) {
            try {
                App.saveCurrentCourseToFile(chosenFile);
                savedStatus = true;
            } catch (IOException e) {
                new Alert(Alert.AlertType.ERROR, "Error saving lesson plan file: " + chosenFile).show();
            }
        }
    }

    @FXML
    private void showToolTips(){
        Alert alreadyAddedAlert = new Alert(AlertType.INFORMATION);
        alreadyAddedAlert.setHeaderText(null);
        alreadyAddedAlert.setTitle("Tool Tips");
        alreadyAddedAlert.setContentText("Left click for card options." + "\n" + "Right click to immediately add or remove cards.");
        alreadyAddedAlert.initOwner(App.primaryStage);
        alreadyAddedAlert.showAndWait();
    }

    //Loading to the lesson
    @FXML
    private void loadMenuAction() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("New File");
        FileChooser.ExtensionFilter filter = new FileChooser.ExtensionFilter("Course Logs (*.coursePlan)", "*.coursePlan");
        fileChooser.getExtensionFilters().add(filter);
        Window mainWindow = displayLesson.getScene().getWindow();
        File chosenFile = fileChooser.showOpenDialog(mainWindow);
        if (chosenFile != null) {
            try {
                App.loadCurrentCourseFromFile(chosenFile);
                displayLesson.getChildren().clear();
                LessonPlan loadedLesson = App.getCurrentCourse().getCurrentLessonPlan();
                lessonTitle.setText(loadedLesson.getTitle());

                for(Card card: loadedLesson.getCopyOfLessonCards()){
                    ImageView cardImageView = new ImageView(card.getImage());
                    cardImageView.setFitWidth(1650/7);
                    cardImageView.setFitHeight(1275/7);
                    setMouseEvent(cardImageView, card, false);
                    displayLesson.getChildren().add(cardImageView);
                }
                savedStatus = true;

            } catch (IOException ex) {
                new Alert(Alert.AlertType.ERROR, "Error loading movie log file: " + chosenFile).show();
            }
        }
    }
//    @FXML
//    private void newMenuAction() throws FileNotFoundException {
//        App.getCurrentCourse().addLessonPlan(new LessonPlan("Untitled"));
//        lessonTitle.setText("Add Lesson Title");
//        displayLesson.getChildren().clear();
//        currentLessonPlan = App.getCurrentCourse().getCurrentLessonPlan();
//    }

    void setEditLessonPlan(LessonPlan lessonPlan, boolean addingNew){
        this.currentLessonPlan = lessonPlan;
        if(addingNew){
            App.getCurrentCourse().addLessonPlan(new LessonPlan("Untitled"));
            lessonTitle.setText("Add Lesson Title");
            displayLesson.getChildren().clear();
            currentLessonPlan = App.getCurrentCourse().getCurrentLessonPlan();
        }else{
            currentLessonPlan = lessonPlan;
            lessonTitle.setText(lessonPlan.getTitle());
            displayLesson.getChildren().clear();
            for(Card card: currentLessonPlan.getCopyOfLessonCards()){
                ImageView cardImageView = new ImageView(card.getImage());
                cardImageView.setFitWidth(1650/7);
                cardImageView.setFitHeight(1275/7);
                setMouseEvent(cardImageView, card, false);
                displayLesson.getChildren().add(cardImageView);
            }

        }
    }
}