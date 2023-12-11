package edu.augustana;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class FavoritesManager {

    final static File favorites = new File("GymManagerAssets/favorites.txt");
    static ArrayList<String> favoritesList = new ArrayList<>();

    static FavoritesManager favoritesManager = new FavoritesManager();
    private FavoritesManager(){initializeFavorites();}

    public static FavoritesManager getFavoritesManager(){
        return favoritesManager;
    }

    /**
     * Stores all favorite card IDs to a list
     * for the card objects to access when they are created
     */
    public static void initializeFavorites(){
        try {
            if (favorites.createNewFile()) {
                System.out.println("File created: " + favorites.getName());
            } else {
                System.out.println("Previous favorites file exists.");
                Scanner favoritesReader = new Scanner(favorites);
                while(favoritesReader.hasNext()) {
                    String[] favoritesData = favoritesReader.nextLine().strip().replaceAll("[\\[\\]]","").split(",");
                    for(int i = 0; i < favoritesData.length; i++) {
                        favoritesData[i] = favoritesData[i].strip();
                    }
                    favoritesList.addAll(Arrays.asList(favoritesData));
                }
            }
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public static ArrayList<String> getFavorites(){
        return favoritesList;
    }

    /**
     * adds a card to the favorites file
     */
    public static void saveToFavorites(String cardID){
        try {
            if(!favoritesList.contains(cardID)){
                favoritesList.add(cardID);
                FileWriter favoritesWriter = new FileWriter(favorites, true);
                favoritesWriter.append(cardID).append(",");
                favoritesWriter.close();
                System.out.println(cardID + " successfully saved to favorites");
                System.out.println("Add2");
            } else {
                System.out.println(cardID + " is already saved to favorites");
            }
        } catch (IOException e) {
            System.out.println("A save error occurred for card " + cardID);
            e.printStackTrace();
        }
    }

    /**
     * removes a card from the favorites file
     */
    public static void removeFromFavorites(String cardID){
        try{
            if(favoritesList.contains(cardID)) {
                favoritesList.remove(cardID);
                FileWriter favoritesWriter = new FileWriter(favorites);
                favoritesWriter.write(String.valueOf(favoritesList));
                favoritesWriter.close();
            }
        } catch (IOException e) {
                System.out.println("A save error occurred for card " + cardID);
                e.printStackTrace();
            }
    }

    public static void main(String[] args){

    }

}
