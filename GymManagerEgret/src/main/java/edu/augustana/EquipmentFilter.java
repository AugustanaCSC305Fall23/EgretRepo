package edu.augustana;

public class EquipmentFilter {
    private String desiredEquipment;
    public EquipmentFilter(String desiredEquipment){
        this.desiredEquipment = desiredEquipment;
    }
    public boolean matches(Card card){
        return card.getEquipment().equals(desiredEquipment);
    }
}
