package edu.augustana;

public class CategoryFilter implements CardFilter{
    private String desiredCategory;
    public CategoryFilter(String desiredCategory){
        this.desiredCategory = desiredCategory;
    }

    @Override
    public boolean matches(Card candidateCard) {
        return candidateCard.getCategory().equals(desiredCategory);
    }
}
