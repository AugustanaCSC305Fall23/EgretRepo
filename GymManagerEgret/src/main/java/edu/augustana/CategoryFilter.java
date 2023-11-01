package edu.augustana;

public class CategoryFilter {
    private String desiredCategory;
    public CategoryFilter(String desiredCategory){
        this.desiredCategory = desiredCategory;
    }
    public boolean event(Card card){
        return card.getCategory().equals(desiredCategory);
    }
}
