package edu.augustana.filters;

import edu.augustana.Card;
import edu.augustana.filters.CardFilter;

public class CodeFilter implements CardFilter {
    private String searchCode= "";
    public CodeFilter(){
        this.searchCode = searchCode;
    }

    public boolean matches(Card candidateCard){
        return candidateCard.getCode().equals(searchCode);
    }

}
