package edu.augustana.filters;

import edu.augustana.Card;
import edu.augustana.filters.CardFilter;

import java.util.ArrayList;

public class EquipmentFilter implements CardFilter {
    private String desiredEquipment;
    public EquipmentFilter(String desiredEquipment){
        this.desiredEquipment = desiredEquipment.toLowerCase();
    }
    public boolean matches(Card candidateCard){
        if(desiredEquipment.equals("all")){
            return true;
        }else {
            ArrayList<String> lowerList = new ArrayList<>();
            for (String item : candidateCard.getEquipment()){
                lowerList.add(item.toLowerCase());
            }
            return lowerList.contains(desiredEquipment);
        }
    }
}
