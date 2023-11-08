package edu.augustana;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Card {
    private final String code;
    private final String event;

    private final String category;

    private final String title;

    private final String pack;

    private final String image;

    private final String gender;

    private final String modelSex;

    private boolean favoriteStatus;

    private final ArrayList<String> level = new ArrayList<>();

    private final ArrayList<String> equipment = new ArrayList<>();

    private final ArrayList<String> keywords = new ArrayList<>();

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
    //Uses the cardData from a line of the CSV
    public Card(String [] cardData) {
        this.code = cardData[0];
        this.event = cardData[1];
        this.category = cardData[2];
        this.title = cardData[3].toLowerCase();
        this.pack = cardData[4];
        this.image = cardData[5];
        this.gender = cardData[6];
        this.modelSex = cardData[7];
        this.favoriteStatus = false;
        ArrayList<String> level = new ArrayList<>(Arrays.asList(cardData[8].split(" ")));
        for (String category : level){
            category = category.trim();
            this.level.add(category);
        }
        ArrayList<String> equipment = new ArrayList<>(Arrays.asList(cardData[9].split(",")));
        for (String item : equipment){
            item = item.trim();
            item = item.toLowerCase();
            this.equipment.add(item);
        }
        ArrayList<String> keywords = new ArrayList<>(Arrays.asList(cardData[10].split(",")));
        for (String word : keywords){
            word = word.trim();
            word = word.toLowerCase();
            this.keywords.add(word);
        }
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

    public String getPack() {
        return pack;
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

    public ArrayList<String> getLevel() {
        return level;
    }

    public ArrayList<String> getEquipment() {
        return equipment;
    }

    public ArrayList<String> getKeywords() {
        return keywords;
    }




}
