package edu.augustana;

import java.io.*;
//import java.lang.reflect.Array;
import java.util.ArrayList;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.TilePane;


public class LessonPlan {
    private ArrayList<String> userChosenCardIDs;
    private String title;

    public LessonPlan() {
    }

    public LessonPlan(String title){
        this.title = title;
        this.userChosenCardIDs = new ArrayList<>();
    }

    public ArrayList<Card> getCopyOfLessonCards() {
        ArrayList<Card> cardList = new ArrayList<>();
        for(String id: userChosenCardIDs){
            cardList.add(CardDatabase.getCardByUniqueId(id));}
        return cardList;
    }
//    public Map<String, List<Card>> getCardsByEvent() {
//
//    }

    public void addCard(Card card){
        userChosenCardIDs.add(card.getUniqueId());
    }

    public void removeCard(Card card){
        userChosenCardIDs.remove(card.getUniqueId());
    }

    public boolean containsCard(Card card){
        return userChosenCardIDs.contains(card.getUniqueId());
    }

    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public void displayCards(double width, double height, TilePane tile){
        for (Card card : getCopyOfLessonCards()) {
            ImageView newCardView = new ImageView();
            Image cardImage = card.getZoomedImage();
            newCardView.setImage(cardImage);
            newCardView.setFitHeight(height);
            newCardView.setFitWidth(width);
            tile.getChildren().add(newCardView);
        }
    }


    @Override
    public String toString() {
        return title + " " + userChosenCardIDs;
    }

}
