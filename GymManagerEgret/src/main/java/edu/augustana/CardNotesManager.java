package edu.augustana;

import java.io.*;
import java.util.HashMap;
import java.util.Scanner;

public class CardNotesManager {

    final static File cardNotesFile = new File("GymManagerAssets/cardNotes.txt");
    static HashMap<String, String> cardNotesMap = new HashMap<>();
    static CardNotesManager cardNotesManager = new CardNotesManager();
    private CardNotesManager(){initializeCardNotes();}

    public static CardNotesManager getCardNotesManager(){
        return cardNotesManager;
    }
    public static void initializeCardNotes(){
        try {
            if (cardNotesFile.createNewFile()) {
                System.out.println("File created: " + cardNotesFile.getName());
            } else {
                System.out.println("Previous cardNotes file exists.");
                Scanner cardNotesReader = new Scanner(cardNotesFile);
                while(cardNotesReader.hasNext()) {
                    String[] cardNotesData = cardNotesReader.nextLine().split(",", 2);
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
                FileWriter cardNotesWriter = new FileWriter(cardNotesFile, true);
                cardNotesString = cardNotesString.replace("\n", " ");
                cardNotesMap.put(cardID, cardNotesString);
                cardNotesWriter.append(cardID).append(",").append(cardNotesString).append("\n");
                cardNotesWriter.close();
                System.out.println(cardID + " successfully saved to cardNotes");
            } else {
                cardNotesMap.remove(cardID);
                cardNotesString = cardNotesString.replace("\n", " ");
                cardNotesMap.put(cardID, cardNotesString);
                String oldContent = "";
                String oldNote = "";
                BufferedReader notesReader = new BufferedReader(new FileReader(cardNotesFile));
                String line =  notesReader.readLine();
                while (line != null){
                    System.out.println("line is " + line);
                    if (line.contains(cardID + ",")){
                        oldNote = line;
                    }
                    oldContent = oldContent + line + System.lineSeparator();
                    line = notesReader.readLine();
                }
                String newNote = cardID + "," +cardNotesString;
                /*System.out.println("oldContent: " + oldContent);
                System.out.println();
                System.out.println("oldNote: " + oldNote);
                System.out.println("newNote: " + newNote);
                System.out.println();*/
                String newContent = oldContent.replaceAll(oldNote, newNote);
                FileWriter cardNotesWriter = new FileWriter(cardNotesFile, false);
                cardNotesWriter.write(newContent);
                cardNotesWriter.close();
                /*System.out.println();
                System.out.println("newContent" + newContent);*/
                //System.out.println(cardID + " is already saved to cardNotes");
            }
        } catch (IOException e) {
            System.out.println("A save error occurred for card " + cardID);
            e.printStackTrace();
        }
    }

    public static void main(String[] args){

    }

}
