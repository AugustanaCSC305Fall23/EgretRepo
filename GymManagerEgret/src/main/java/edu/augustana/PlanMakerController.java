package edu.augustana;

import java.io.*;
import java.util.*;
import edu.augustana.filters.*;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.print.*;
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
import org.controlsfx.control.ToggleSwitch;


public class PlanMakerController {

    private final ArrayList<Card> allCards = CardDatabase.allCards;
    private static LessonPlan currentLessonPlan;

    private LessonPlanUndoRedoHandler undoRedoHandler;
    private String enteredTitle;

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
    private TextField superSearchBox;
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

    CardNotesManager cardNotesManager = CardNotesManager.getCardNotesManager();


    public PlanMakerController() {
        currentLessonPlan = App.getCurrentCourse().getCurrentLessonPlan();
    }

    @FXML
    void connectToHomePage() {
        App.switchToHomePageView();
    }


    @FXML
    private void exitPlatform() {
        Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to exit?");
        DialogPane dialogPane = confirmation.getDialogPane();
        dialogPane.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
        confirmation.initOwner(App.primaryStage);
        confirmation.setHeaderText(null);

        ButtonType yesButton = new ButtonType("Yes", ButtonBar.ButtonData.YES);
        ButtonType noButton = new ButtonType("No", ButtonBar.ButtonData.NO);

        confirmation.getButtonTypes().setAll(yesButton, noButton);

        confirmation.showAndWait().ifPresent(response -> {
            if (response == yesButton) {
                Platform.exit();
            } else {
                confirmation.close();
            }
        });
    }

    /**
     * LessonTitle, undoRedo, and homepage are reset when the PlanMaker is opened.
     */

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
                Label tipLabel = generateTooltip(cardImageView, false);
                displayLesson.getChildren().add(tipLabel);
                setMouseEvent(tipLabel, card, false);
                displayLesson.getChildren().add(cardImageView);
            }
        }
        currentLessonPlan.setTitle(lessonTitle.getText());
        undoRedoHandler = new LessonPlanUndoRedoHandler(this);
        print.setOnAction(event -> previewOptions());
    }


    private void showImagePopup(Card card, boolean addOrRemove) {
        Alert imageAlert = new Alert(AlertType.INFORMATION);
        DialogPane dialogPane = imageAlert.getDialogPane();
        dialogPane.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
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
        ButtonType printCard = new ButtonType("Print Card");
        ButtonType addNotes = new ButtonType("Notes");
        ButtonType placeCardButtonType = new ButtonType(placeButtonText);
        ButtonType toggleFavoriteButton = new ButtonType(favoriteButtonText);
        imageAlert.getButtonTypes().setAll(placeCardButtonType, toggleFavoriteButton, printCard, addNotes, ButtonType.CANCEL);
        imageAlert.setResultConverter(buttonType -> {
            if (buttonType == placeCardButtonType) {
                if(addOrRemove){
                    imageAlert.close();
                    addCardToPlan(card);
                } else {
                    imageAlert.close();
                    removeCardFromPlan(card);
                }

            }else if(buttonType==printCard) {
                printContent(card.getZoomedImage());
            }else if(buttonType==addNotes){
                addCardNotes(card, addOrRemove);
            }else if(buttonType == toggleFavoriteButton){
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
    private void setMouseEvent(Label cardPane, Card card, boolean addOrRemove){
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
    private void addCardNotes(Card card, boolean addOrRemove){
        Alert addNotesAlert = new Alert(AlertType.INFORMATION);
        DialogPane dialogPane = addNotesAlert.getDialogPane();
        dialogPane.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
        addNotesAlert.initOwner(App.primaryStage);
        addNotesAlert.setHeaderText(null);
        addNotesAlert.setTitle("Add Notes");

        TextArea textArea = new TextArea();
        textArea.setWrapText(true);
        textArea.setText(card.getCardNotes());
        System.out.println(card.getCardNotes());
        textArea.selectAll();

        VBox contentVBox = new VBox(textArea);
        contentVBox.setAlignment(Pos.CENTER);
        contentVBox.setSpacing(10);
        addNotesAlert.getDialogPane().setContent(contentVBox);
        ButtonType addNotes = new ButtonType("Done");
        addNotesAlert.getButtonTypes().setAll(addNotes, ButtonType.CANCEL);
        addNotesAlert.show();
        addNotesAlert.setResultConverter(buttonType -> {
            if (buttonType == addNotes) {
                card.setCardNotes(textArea.getText());
                //System.out.println(textArea.getText());
                cardNotesManager.saveCardNotes(card.getCode(), textArea.getText().replace('(', ' ').replace(')', ' '));
                addNotesAlert.close();
                //System.out.println(card.getCardNotes());
            } else if (buttonType == ButtonType.CANCEL){
                addNotesAlert.close();
            }
            return buttonType;
        });
    }
    private void printContent(Image image) {
        System.out.println("printContent called: " + image);
        PrinterJob job = PrinterJob.createPrinterJob();
        System.out.println("Job=" + job);

        if (job != null) {
            PageLayout pageLayout = job.getPrinter().createPageLayout(Paper.A4, PageOrientation.LANDSCAPE, Printer.MarginType.EQUAL);
            job.getJobSettings().setPageLayout(pageLayout);

            VBox cardVBox = new VBox();
            cardVBox.setAlignment(Pos.CENTER);

            ImageView cardImageView = new ImageView(image);
            cardImageView.setFitWidth(1650/2.5);
            cardImageView.setFitHeight(1275/2.5);
            cardVBox.getChildren().add(cardImageView);

            boolean showDialog = job.showPrintDialog(App.primaryStage);

            if (showDialog) {
                boolean success = job.printPage(cardVBox);
                System.out.println("Print success: " + success);
                job.endJob();
            } else {
                job.cancelJob();
            }
        }
    }

    private Label generateTooltip(ImageView cardImageView, boolean addOrRemove){
        Label tipLabel = new Label();
        tipLabel.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
        tipLabel.setGraphic(cardImageView);
        //tipLabel.setMouseTransparent(true);
        if (addOrRemove) {
            tipLabel.setTooltip(new Tooltip("Left click for details." + "\n" + "Right click to add card to plan."));
        } else {
            tipLabel.setTooltip(new Tooltip("Left click for details." + "\n" + "Right click to remove card from plan."));
        }
        return tipLabel;
    }

    private void addCardToPlan(Card card){
        if (!currentLessonPlan.containsCard(card)) {
            ImageView cardImageView = new ImageView(card.getImage());
            cardImageView.setFitWidth(1650/7);
            cardImageView.setFitHeight(1275/7);
            Label tipLabel = generateTooltip(cardImageView, false);
            displayLesson.getChildren().add(tipLabel);
            displayLesson.getChildren().add(cardImageView);
            setMouseEvent(tipLabel, card, false);
            currentLessonPlan.addCard(card);
            undoRedoHandler.saveState();
        }else{
            Alert alreadyAddedAlert = new Alert(AlertType.INFORMATION);
            DialogPane dialogPane = alreadyAddedAlert.getDialogPane();
            dialogPane.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
            alreadyAddedAlert.setHeaderText(null);
            alreadyAddedAlert.setTitle(null);
            alreadyAddedAlert.setContentText("Already added card");
            alreadyAddedAlert.initOwner(App.primaryStage);
            alreadyAddedAlert.showAndWait();
        }
    }

    private void removeCardFromPlan(Card card){
        currentLessonPlan.removeCard(card);
        undoRedoHandler.saveState();
        try {
            updateLessonDisplay();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }


    public void updateLessonDisplay() throws FileNotFoundException {
        displayLesson.getChildren().clear();
        for (Card card : currentLessonPlan.getCopyOfLessonCards()) {
            System.out.println(card);
            ImageView cardImageView = new ImageView(card.getImage());
            cardImageView.setFitWidth(1650 /7);
            cardImageView.setFitHeight(1275 /7);
            Label tipLabel = generateTooltip(cardImageView, false);
            displayLesson.getChildren().add(tipLabel);
            displayLesson.getChildren().add(cardImageView);
            setMouseEvent(tipLabel, card, false);
        }
    }

    private void previewOptions(){

        Alert imageAlert = new Alert(AlertType.INFORMATION);
        imageAlert.initOwner(App.primaryStage);
        imageAlert.setHeaderText(null);
        imageAlert.setTitle("Preview/Print Options");
        DialogPane dialogPane = imageAlert.getDialogPane();
        dialogPane.getStylesheets().add(getClass().getResource("style.css").toExternalForm());

        ButtonType printCards = new ButtonType("Cards Preview/Print");
        ButtonType printCardsTitles = new ButtonType("Codes and Lesson Title Preview/Print");
        ButtonType printCardsEquipment = new ButtonType("Cards with Equipment Preview/Print");

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
            Label tipLabel = generateTooltip(newCardView, true);
            cardFlowPane.getChildren().add(newCardView);
            cardFlowPane.getChildren().add(tipLabel);
            setMouseEvent(tipLabel, card, true);


        }
    }
    /**
     * @param - LessonPlan, boolean
     * Checks the boolean value and makes a new lessonPlan in the course or
     * opens the lessonPlan if the selected LessonPlan exists
     */
    public void setEditLessonPlan(LessonPlan lessonPlan, boolean addingNew){
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
                Label tipLabel = generateTooltip(cardImageView, false);
                displayLesson.getChildren().add(tipLabel);
                displayLesson.getChildren().add(cardImageView);
                setMouseEvent(tipLabel, card, false);
                undoRedoHandler.saveState();
            }
        }
    }
    @FXML
    private void newMenuAction() {
        App.getCurrentCourse().addLessonPlan(new LessonPlan("Untitled"));
        lessonTitle.setText("Add Lesson Title");
        displayLesson.getChildren().clear();
        currentLessonPlan = App.getCurrentCourse().getCurrentLessonPlan();
        undoRedoHandler.saveState();
    }

    @FXML
    private void connectToCoursePage(){
        App.switchCourseView();
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
    public void onTitleClick() {
        lessonTitle.setVisible(false);
        lessonTitleTextArea.setVisible(true);
        lessonTitleTextArea.setText(lessonTitle.getText());
        lessonTitleTextArea.requestFocus();
        edit.setDisable(true);
    }
    @FXML
    public void onEnterPressed(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            String editedText = lessonTitleTextArea.getText();
            lessonTitle.setText(editedText);
            enteredTitle = editedText;
            lessonTitleTextArea.setVisible(false);
            currentLessonPlan.setTitle(enteredTitle);
            lessonTitle.setVisible(true);
            edit.setDisable(false);
            undoRedoHandler.saveState();
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
    public void searchSuper(){
        CardDatabase.addFilter(new TextFilter(superSearchBox.getText().toLowerCase()));
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
    private void resetFilters() throws FileNotFoundException {
        categoryChoiceBox.setValue("ALL");
        eventChoiceBox.setValue("ALL");
        equipmentChoiceBox.setValue("ALL");
        CardDatabase.clearFilters();
        setCardDisplay(allCards);
        favoriteSwitch.setSelected(false);
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
    private void showAbout() {
        Alert aboutAlert = new Alert(Alert.AlertType.INFORMATION);
        DialogPane dialogPane = aboutAlert.getDialogPane();
        dialogPane.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
        aboutAlert.setHeaderText("Credits");
        aboutAlert.setTitle("About");
        aboutAlert.setContentText("Product Designer: " + "\n" + "   Samantha Keehn" + "\n"
                + "Developers: " + "\n" +"  Riva Kansakar" + "\n" +"  Drake Misfeldt" + "\n" +"  Stuti Shrestha" + "\n"
                + "Project Supervisor: " + "\n" + " Forrest Stonedahl");
        aboutAlert.initOwner(App.primaryStage);
        aboutAlert.showAndWait();
    }

    @FXML
    private void showToolTips(){
        Alert alreadyAddedAlert = new Alert(AlertType.INFORMATION);
        DialogPane dialogPane = alreadyAddedAlert.getDialogPane();
        dialogPane.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
        alreadyAddedAlert.setHeaderText(null);
        alreadyAddedAlert.setTitle("Navigate");
        alreadyAddedAlert.setContentText("Left click for card options." + "\n" +
                "Right click to immediately add or remove cards." + "\n" + "Click on the title to rename title");
        alreadyAddedAlert.initOwner(App.primaryStage);
        alreadyAddedAlert.showAndWait();
    }

    @FXML
    private void onEditMenuUndo() throws FileNotFoundException {undoRedoHandler.undo(); updateLessonDisplay();}
    @FXML
    private void onEditMenuRedo() throws FileNotFoundException {undoRedoHandler.redo(); updateLessonDisplay();}

    public State createMemento() {
        return new State();
    }

    public void restoreState(State planMakerState) {
        planMakerState.restore();
        //repaint();
    }

    public class State {
        LessonPlan lesson;

        public State() {
            lesson = (LessonPlan) PlanMakerController.currentLessonPlan.clone();
        }

        public void restore() {
            PlanMakerController.currentLessonPlan = (LessonPlan) lesson.clone();
        }

        @Override
        public String toString() {
            return currentLessonPlan.toString();
        }
    }

}