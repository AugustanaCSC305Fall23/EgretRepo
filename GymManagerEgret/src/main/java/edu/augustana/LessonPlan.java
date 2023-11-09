package edu.augustana;


import java.io.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


public class LessonPlan {
    private ArrayList<String> userChosenCardIDs;
    private String title;

    public LessonPlan(){

    }
    public LessonPlan(String title){
        this.title = title;
        this.userChosenCardIDs = new ArrayList<>();
    }

    public ArrayList<Card> getCopyOfLessonCards() {
        ArrayList<Card> cardList = new ArrayList<>();
        for(String id: userChosenCardIDs){
            cardList.add(CardDatabase.getCardByUniqueId(id));
        }
        return cardList;
    }


    public void addCard(Card card){
        userChosenCardIDs.add(card.getUniqueId());
    }


    public void removeCard(Card card){
        userChosenCardIDs.remove(card.getUniqueId());
    }


    public boolean containsCard(Card card){
        return userChosenCardIDs.contains(card.getUniqueId());
    }
    public String getTitle() {
        return title;
    }


    public void setTitle(String title) {
        this.title = title;
    }


    public static LessonPlan loadFromFile(File logFile)throws IOException{
        FileReader reader = new FileReader(logFile);
        Gson gson = new Gson();
        return gson.fromJson(reader,LessonPlan.class);


    }

    public void saveToFile(File logFile) throws IOException{
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String serializedLessonPlanText = gson.toJson(this);
        PrintWriter writer = new PrintWriter(new FileWriter(logFile));
        writer.println(serializedLessonPlanText);
        writer.close();
    }



}
