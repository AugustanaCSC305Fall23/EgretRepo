package edu.augustana;

import java.util.ArrayList;

public class LessonPlan {
    private static LessonPlan instance;
    private ArrayList<Card> lessonCards = new ArrayList<>();

    private static String title;

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


    public LessonPlan(String title){
        this.title = title;
    }
    public String getTitle(){
        return title;
    }

    public void setTitle(String title){
        this.title = title;

    }
    public void addCard(Card card){
        lessonCards.add(card);
    }

    public void removeCard(Card card){
        lessonCards.remove(card);
    }


}
