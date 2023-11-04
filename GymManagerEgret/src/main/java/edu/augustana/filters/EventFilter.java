package edu.augustana.filters;

import edu.augustana.Card;
import edu.augustana.filters.CardFilter;

public class EventFilter implements CardFilter {
    private String desiredEvent;
    public EventFilter(String desiredEvent){
        this.desiredEvent = desiredEvent;
    }
    public boolean matches(Card candidateCard){
        return candidateCard.getEvent().equals(desiredEvent);
    }
}
