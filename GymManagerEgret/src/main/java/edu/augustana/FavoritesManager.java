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
    public void cleanFavoritesList() throws IOException {
        String listString =  favoritesList.toString().replace(", ", "");
        FileWriter favoritesWriter = new FileWriter(favorites, false);
        favoritesWriter.append(listString);
        favoritesWriter.close();
    }

    public static void main(String[] args){
        initializeFavorites();
        saveToFavorites("C1");
        saveToFavorites("C2");
        saveToFavorites("C2");
        saveToFavorites("C3");
        System.out.println(favoritesList);
        removeFromFavorites("C2");
        System.out.println(favoritesList);
    }

}
