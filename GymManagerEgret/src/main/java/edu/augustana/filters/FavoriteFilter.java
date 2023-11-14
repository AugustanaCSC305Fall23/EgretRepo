package edu.augustana.filters;

import edu.augustana.Card;

public class FavoriteFilter implements CardFilter{
    public FavoriteFilter(){}
    @Override
    public boolean matches(Card candidateCard) {
        return candidateCard.getFavoriteStatus();
    }
}
