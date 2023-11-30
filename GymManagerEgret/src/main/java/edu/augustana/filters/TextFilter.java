package edu.augustana.filters;

import edu.augustana.Card;
import edu.augustana.filters.CardFilter;

public class TextFilter implements CardFilter {
    private String searchText = " ";

    public TextFilter(String searchText){
        this.searchText = searchText;
    }

    public boolean matches(Card candidateCard){
        if (candidateCard.getTitle().toLowerCase().contains(searchText)){
            return true;
        }
        if (candidateCard.getCode().toLowerCase().contains(searchText)){
            return true;
        }
        if (candidateCard.getEvent().toLowerCase().contains(searchText)){
            return true;
        }
        if (candidateCard.getCategory().toLowerCase().contains(searchText)){
            return true;
        }
        for(String item : candidateCard.getEquipment()) {
            if (item.contains(searchText)) {
                return true;
            }
        }
        for(String word : candidateCard.getKeywords()) {
            if (word.contains(searchText)) {
                return true;
            }
        }
        return false;
    }
}
