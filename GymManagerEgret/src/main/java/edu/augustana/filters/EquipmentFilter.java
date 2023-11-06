package edu.augustana.filters;

import edu.augustana.Card;
import edu.augustana.filters.CardFilter;

public class EquipmentFilter implements CardFilter {
    private String desiredEquipment;
    public EquipmentFilter(String desiredEquipment){
        this.desiredEquipment = desiredEquipment;
    }
    public boolean matches(Card candidateCard){
        if(desiredEquipment.equals("ALL")){
            return true;
        }else {
            return candidateCard.getEquipment().contains(desiredEquipment);
        }
    }
}
