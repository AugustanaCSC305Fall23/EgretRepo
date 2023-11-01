package edu.augustana;

public class TitleFilter {
    private String searchTitle = " ";

    public TitleFilter(String searchTitle){
        this.searchTitle = searchTitle;
    }

    public boolean matches(Card card){
        return card.getTitle().equals(searchTitle);
    }
}
