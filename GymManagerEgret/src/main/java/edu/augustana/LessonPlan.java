package edu.augustana;

import java.util.ArrayList;
import java.util.TreeMap;

public class LessonPlan {
    private ArrayList<Card> cards;

    private String title;

    public LessonPlan(String title){
        this.title = title;
    }

    public void addCard(Card card){
        cards.add(card);
    }

    public void removeCard(Card card){
        cards.remove(card);
    }


}
