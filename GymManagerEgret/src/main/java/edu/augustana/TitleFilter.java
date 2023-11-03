package edu.augustana;

public class TitleFilter implements CardFilter{
    private String searchTitle = " ";

    public TitleFilter(String searchTitle){
        this.searchTitle = searchTitle;
    }

    public boolean matches(Card candidateCard){
        return candidateCard.getTitle().equals(searchTitle);
    }
}
