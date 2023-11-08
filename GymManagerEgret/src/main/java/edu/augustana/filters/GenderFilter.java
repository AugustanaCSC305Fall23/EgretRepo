package edu.augustana.filters;

import edu.augustana.Card;
import edu.augustana.filters.CardFilter;

public class GenderFilter implements CardFilter {
    private String desiredGender = " ";
    public GenderFilter(String desiredGender){
        this.desiredGender = desiredGender;
    }

    public boolean matches(Card candidateCard){
        if(desiredGender.equals("ALL")) {
            return true;
        } else{
            return candidateCard.getGender().equals(desiredGender);
        }
    };
}
