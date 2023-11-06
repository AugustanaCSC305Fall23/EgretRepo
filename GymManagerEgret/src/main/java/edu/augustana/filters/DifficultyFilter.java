package edu.augustana.filters;

import edu.augustana.Card;
import edu.augustana.filters.CardFilter;

public class DifficultyFilter implements CardFilter {
    private String searchDifficulty;
    public DifficultyFilter(String searchDifficulty){
        this.searchDifficulty = searchDifficulty;
    }

    public boolean matches(Card candidateCard){
        return candidateCard.getLevel().contains(searchDifficulty);
    }
}
