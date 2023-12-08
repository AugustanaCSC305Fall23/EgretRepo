package edu.augustana;

import java.util.ArrayList;


/**
 * LessonPlans store all the cards in one lesson
 */
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

    /**
     * @return a copy of the cards in the lesson
     */
    public ArrayList<Card> getCopyOfLessonCards() {
        ArrayList<Card> cardList = new ArrayList<>();
        for(String id: userChosenCardIDs){
            cardList.add(CardDatabase.getCardByUniqueId(id));}
        return cardList;
    }

    /**
     * Add cards to the lesson
     */

    public void addCard(Card card){
        userChosenCardIDs.add(card.getUniqueId());
    }

    /**
     * Remove cards of the lesson
     */

    public void removeCard(Card card){
        userChosenCardIDs.remove(card.getUniqueId());
    }

    /**
     * @return if the card is in lesson or not
     */

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
