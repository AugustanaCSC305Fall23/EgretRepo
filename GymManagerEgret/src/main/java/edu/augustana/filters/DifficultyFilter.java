package edu.augustana.filters;

import edu.augustana.Card;
import edu.augustana.filters.CardFilter;

public class DifficultyFilter implements CardFilter {
    private char searchDifficulty;
    public DifficultyFilter(char searchDifficulty){
        this.searchDifficulty = searchDifficulty;
    }

    public boolean matches(Card candidateCard){
        return candidateCard.getLevel().equals(searchDifficulty);
    }
}
