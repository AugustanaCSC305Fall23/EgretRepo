package edu.augustana.filters;

import edu.augustana.Card;

public interface CardFilter {
    public boolean matches(Card candidateCard);
}
