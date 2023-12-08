package edu.augustana;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;

public class CardNotesManager {

    final static File cardNotes = new File("GymManagerAssets/cardNotes.txt");
    static HashMap<String, String> cardNotesMap = new HashMap<>();
    static CardNotesManager cardNotesManager = new CardNotesManager();
    private CardNotesManager(){initializeCardNotes();}

    public static CardNotesManager getCardNotesManager(){
        return cardNotesManager;
    }
    public static void initializeCardNotes(){
        try {
            if (cardNotes.createNewFile()) {
                System.out.println("File created: " + cardNotes.getName());
            } else {
                System.out.println("Previous cardNotes file exists.");
                Scanner cardNotesReader = new Scanner(cardNotes);
                while(cardNotesReader.hasNext()) {
                    String[] cardNotesData = cardNotesReader.nextLine().split(",");
                    cardNotesMap.put(cardNotesData[0], cardNotesData[1]);
                }
            }
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public static HashMap<String, String> getCardNotes(){
        return cardNotesMap;
    }

    public static void saveCardNotes(String cardID, String cardNotesString){
        try {
            if(!cardNotesMap.containsKey(cardID)){
                cardNotesMap.put(cardID, cardNotesString);
                FileWriter cardNotesWriter = new FileWriter(cardNotes, true);
                cardNotesWriter.append(cardID).append(",").append(cardNotesString).append("\n");
                cardNotesWriter.close();
                System.out.println(cardID + " successfully saved to cardNotes");
            } else {
                System.out.println(cardID + " is already saved to cardNotes");
            }
        } catch (IOException e) {
            System.out.println("A save error occurred for card " + cardID);
            e.printStackTrace();
        }
    }

    public static void main(String[] args){

    }

}
