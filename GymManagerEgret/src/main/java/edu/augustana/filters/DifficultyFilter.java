package edu.augustana.filters;

import edu.augustana.Card;
import edu.augustana.filters.CardFilter;

public class DifficultyFilter implements CardFilter {
    private String searchDifficulty;
    public DifficultyFilter(String searchDifficulty){
        this.searchDifficulty = searchDifficulty;
    }

    public boolean matches(Card candidateCard){
        if(searchDifficulty.equals("ALL")) {
            return true;
        } else if (candidateCard.getLevel().contains("ALL")) {
            return true;
        }else{
            return candidateCard.getLevel().contains(searchDifficulty);
        }
    }
}
