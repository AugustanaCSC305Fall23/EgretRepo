package edu.augustana.filters;

import edu.augustana.Card;
import edu.augustana.filters.CardFilter;

public class CategoryFilter implements CardFilter {
    private String desiredCategory;
    public CategoryFilter(String desiredCategory){
        this.desiredCategory = desiredCategory;
    }

    @Override
    public boolean matches(Card candidateCard) {
        if (desiredCategory.equals("ALL")) {
            return true;
        } else {
            return candidateCard.getCategory().equals(desiredCategory);
        }
    }
    @Override
    public String toString() {
        return desiredCategory;
    }
}
