package edu.augustana;

public class CodeFilter implements CardFilter{
    private String searchCode= "";
    public CodeFilter(){
        this.searchCode = searchCode;
    }

    public boolean matches(Card candidateCard){
        return candidateCard.getCode().equals(searchCode);
    }

}
