package edu.augustana.filters;

import edu.augustana.Card;
import edu.augustana.filters.CardFilter;

public class GenderFilter implements CardFilter {
    private String desiredGender = " ";
    public GenderFilter(String desiredGender){
        this.desiredGender = desiredGender;
    }

    public boolean matches(Card candidateCard){
        return candidateCard.getGender().equals(desiredGender);
    };
}
