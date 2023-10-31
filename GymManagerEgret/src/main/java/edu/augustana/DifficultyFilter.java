package edu.augustana;

public class DifficultyFilter {
    private char searchDifficulty;
    public DifficultyFilter(char searchDifficulty){
        this.searchDifficulty = searchDifficulty;
    }

    public boolean matches(Card card){
        return card.getLevel().equals(searchDifficulty);
    }
}
