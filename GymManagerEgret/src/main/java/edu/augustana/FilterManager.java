package edu.augustana;

import java.util.ArrayList;

public class FilterManager {
    public static ArrayList<Card> allCards = new ArrayList<>();
    public static ArrayList<Card> filteredCards = allCards;
    FilterManager(){};
    public void addCard(Card card){
        allCards.add(card);
    };

    public void printCards(){
        System.out.println(filteredCards);
    }

    public void filterFloor(){
        filteredCards.removeIf(card -> !"Floor".equals(card.getEvent()));
    }




}
