package edu.augustana;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvValidationException;


public class CardDatabase {
    public static ArrayList<Card> allCards = new ArrayList<>();
    public static ArrayList<Card> filteredCards = allCards;
    CardDatabase(){};

    public void addCardsFromCSV() throws IOException, CsvValidationException {
        CSVReader reader = new CSVReaderBuilder(new FileReader("DEMO1.csv")).build();
        String [] nextLine;
        while ((nextLine = reader.readNext()) != null) {
            // nextLine[] is an array of values from the line
            Card card = new Card(nextLine);
            System.out.println(card);
            addCard(card);
        }
    }
    public void addCard(Card card){
        allCards.add(card);
    };

    public void printCards(){
        System.out.println(filteredCards);
    }

    /*public void filterFloor(){
        filteredCards.removeIf(card -> !"Floor".equals(card.getEvent()));
    }*/




}
