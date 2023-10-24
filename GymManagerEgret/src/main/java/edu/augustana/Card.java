package edu.augustana;

import java.util.ArrayList;

public class Card {
    private final String code;
    private final String event;

    private final String category;

    private final String title;

    private final String image;

    private final String gender;

    private final String modelSex;

    private final String level;

    private final ArrayList<String> equipment;

    private final ArrayList<String> keywords;

    @Override
    public String toString() {
        return "Card{" +
                "code='" + code + '\'' +
                ", event='" + event + '\'' +
                ", category='" + category + '\'' +
                ", title='" + title + '\'' +
                ", image='" + image + '\'' +
                ", gender='" + gender + '\'' +
                ", modelSex='" + modelSex + '\'' +
                ", level='" + level + '\'' +
                ", equipment=" + equipment +
                ", keywords=" + keywords +
                '}';
    }

    public Card(String code, String event, String category, String title,
                String image, String gender, String modelSex, String level, ArrayList<String> equipment, ArrayList<String> keywords) {
        this.code = code;
        this.event = event;
        this.category = category;
        this.title = title;
        this.image = image;
        this.gender = gender;
        this.modelSex = modelSex;
        this.level = level;
        this.equipment = equipment;
        this.keywords = keywords;
    }

    public String getCode() {
        return code;
    }

    public String getEvent() {
        return event;
    }

    public String getCategory() {
        return category;
    }

    public String getTitle() {
        return title;
    }

    public String getImage() {
        return image;
    }

    public String getGender() {
        return gender;
    }

    public String getModelSex() {
        return modelSex;
    }

    public String getLevel() {
        return level;
    }

    public ArrayList<String> getEquipment() {
        return equipment;
    }

    public ArrayList<String> getKeywords() {
        return keywords;
    }




}
