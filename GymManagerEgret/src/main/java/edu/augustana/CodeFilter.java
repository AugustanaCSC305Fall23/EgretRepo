package edu.augustana;

public class CodeFilter {
    private String searchCode= "";
    public CodeFilter(){
        this.searchCode = searchCode;
    }

    public boolean matches(Card card){
        return card.getCode().equals(searchCode);
    }

}
