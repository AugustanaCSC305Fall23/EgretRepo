package edu.augustana;

import javafx.scene.image.Image;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;

public class Card {
    private final String code;
    private final String event;

    private final String category;

    private final String title;

    private final String pack;

    private final String imageFileName;
    private final Image image;

    private final String gender;

    private final String modelSex;

    private boolean favoriteStatus;

    private static FavoritesManager favoritesManager = FavoritesManager.getFavoritesManager();

    private final ArrayList<String> initialFavoritesList = favoritesManager.getFavorites();

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
                ", image='" + imageFileName + '\'' +
                ", gender='" + gender + '\'' +
                ", modelSex='" + modelSex + '\'' +
                ", level='" + level + '\'' +
                ", equipment=" + equipment +
                ", keywords=" + keywords +
                '}';
    }
    //Uses the cardData from a line of the CSV
    public Card(String [] cardData) throws FileNotFoundException {
        this.code = cardData[0];
        this.event = cardData[1];
        this.category = cardData[2];
        this.title = cardData[3];
        this.pack = cardData[4];
        this.imageFileName = cardData[5];
        System.out.println("GymManagerAssets/cardPacks/" + pack + "/thumbs/" + generateThumbnail());
        this.image = new Image(new FileInputStream("GymManagerAssets/cardPacks/" + pack +"/thumbs/" + generateThumbnail()));
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
        if(initialFavoritesList.contains(code)){
            favoriteStatus = true;
        }
    }

    private String generateThumbnail(){
        return imageFileName.replace(".png",".jpg");
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

    public String getImageFileName() {
        return imageFileName;
    }

    public String getUniqueId(){
        return pack+"/"+ imageFileName;
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

    public boolean getFavoriteStatus() { return favoriteStatus;}

    public void toggleFavorite(){
        favoriteStatus = !favoriteStatus;
    }

    public Image getImage() {return image;}

    public Image getZoomedImage(){
        System.out.println("GymManagerAssets/cardPacks/" + pack +"/" + imageFileName);
        try {
            return new Image(new FileInputStream("GymManagerAssets/cardPacks/" + pack +"/" + imageFileName));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
