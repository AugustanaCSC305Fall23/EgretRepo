package edu.augustana;

public class EquipmentFilter implements CardFilter{
    private String desiredEquipment;
    public EquipmentFilter(String desiredEquipment){
        this.desiredEquipment = desiredEquipment;
    }
    public boolean matches(Card candidateCard){
        return candidateCard.getEquipment().equals(desiredEquipment);
    }
}
