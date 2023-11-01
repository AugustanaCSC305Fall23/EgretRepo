package edu.augustana;

public class EventFilter {
    private String desiredEvent;
    public EventFilter(String desiredEvent){
        this.desiredEvent = desiredEvent;
    }
    public boolean event(Card card){
        return card.getEvent().equals(desiredEvent);
    }
}
