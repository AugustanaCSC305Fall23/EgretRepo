package edu.augustana.filters;

import edu.augustana.Card;

public class CodeFilter implements CardFilter {
    private String searchCode= "";
    public CodeFilter(String searchCode){
        this.searchCode = searchCode;
    }

    public boolean matches(Card candidateCard){
        return candidateCard.getCode().contains(searchCode);
    }

}
