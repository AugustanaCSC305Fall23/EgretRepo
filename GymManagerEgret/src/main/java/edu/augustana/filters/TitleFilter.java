package edu.augustana.filters;

import edu.augustana.Card;
import edu.augustana.filters.CardFilter;

public class TitleFilter implements CardFilter {
    private String searchTitle = " ";

    public TitleFilter(String searchTitle){
        this.searchTitle = searchTitle.toLowerCase();
    }

    public boolean matches(Card candidateCard){
        return candidateCard.getTitle().toLowerCase().contains(searchTitle);
    }
}
