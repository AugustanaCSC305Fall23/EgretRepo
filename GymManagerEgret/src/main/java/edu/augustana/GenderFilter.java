package edu.augustana;

public class GenderFilter implements CardFilter {
    private String desiredGender = " ";
    public GenderFilter(String desiredGender){
        this.desiredGender = desiredGender;
    }

    public boolean matches(Card candidateCard){
        return candidateCard.getGender().equals(desiredGender);
    };
}
