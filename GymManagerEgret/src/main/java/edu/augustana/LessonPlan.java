package edu.augustana;

import javafx.scene.image.Image;

import java.util.ArrayList;
import java.util.TreeMap;

public class LessonPlan {
    private static LessonPlan instance;
    private ArrayList<Card> lessonCards = new ArrayList<>();

    private LessonPlan() {
    }

    public static LessonPlan getInstance() {
        if (instance == null) {
            instance = new LessonPlan();
        }
        return instance;
    }

    public ArrayList<Card> getLessonCards() {
        return lessonCards;
    }

    public void setLessonCards(ArrayList<Card> cards) {
        lessonCards = cards;
    }
//    private ArrayList<Card> cards;
//
//    private String title;
//
//    public LessonPlan(String title){
//        this.title = title;
//    }
//
    public void addCard(Card card){
        lessonCards.add(card);
    }

    public void removeCard(Card card){
        lessonCards.remove(card);
    }


}
