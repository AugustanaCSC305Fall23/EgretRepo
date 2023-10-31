package edu.augustana;

public class CategoryFilter {
    private String category;
    public CategoryFilter(String category){
        this.category = category;
    }
    public boolean event(Card card){
        return card.getCategory().equals(category);
    }
}
