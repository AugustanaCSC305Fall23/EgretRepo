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


public class LessonPlan implements Cloneable{
    private ArrayList<String> userChosenCardIDs;
    private String title;

    public LessonPlan(String title){
        this.title = title;
        this.userChosenCardIDs = new ArrayList<>();
    }

    public LessonPlan(LessonPlan original) {
        this.title = original.title;
        this.userChosenCardIDs = new ArrayList<>(original.userChosenCardIDs);
    }

    public ArrayList<Card> getCopyOfLessonCards() {
        ArrayList<Card> cardList = new ArrayList<>();
        for(String id: userChosenCardIDs){
            cardList.add(CardDatabase.getCardByUniqueId(id));}
        return cardList;
    }

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

    @Override
    public String toString() {
        return title + " " + userChosenCardIDs;
    }

    @Override
    public LessonPlan clone() {
        try {
            LessonPlan clone = (LessonPlan) super.clone();
            clone.title = title;
            clone.userChosenCardIDs = new ArrayList<String>();
            clone.userChosenCardIDs.addAll(userChosenCardIDs);
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
