package edu.augustana;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvValidationException;
import edu.augustana.filters.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Label;


public class CardDatabase {

    public static ArrayList<Card> allCards = new ArrayList<>();
    private static HashMap<String, Card> uniqueIdToCardMap = new HashMap<>();
    //public static ArrayList<Card> filteredCards = allCards;
    public static ObservableList<CardFilter> activeFilters = FXCollections.observableArrayList();
    CardDatabase(){};

    public static void addCardsFromCSV() throws IOException, CsvValidationException {
        //reads through the CSV file, skipping the headers in the first line.
        File spreadsheetFile = new File("GymManagerEgret/GymManagerAssets/cardSpreadsheets");
            for(File filename : spreadsheetFile.listFiles()) {
                System.out.println(filename);
                CSVReader reader = new CSVReaderBuilder(new FileReader(filename)).withSkipLines(1).build();
                String[] nextLine;
                while ((nextLine = reader.readNext()) != null) {
                    // nextLine[] is an array of values from the line
                    Card card = new Card(nextLine);
                    System.out.println(card);
                    addCard(card);
                }
            }
    }
    private static void addCard(Card card){
        allCards.add(card);
        uniqueIdToCardMap.put(card.getUniqueId(),card);
    };

    public static Card getCardByUniqueId(String id){
        return uniqueIdToCardMap.get(id);
    }

    public void printCards(){
        System.out.println(allCards);
    }

    public static ArrayList<Card> getAllCards() {
        return allCards;
    }

    /*public void filterFloor(){
        filteredCards.removeIf(card -> !"Floor".equals(card.getEvent()));
    }*/

    public static void addFilter(CardFilter filter){
        activeFilters.removeIf(currentFilter -> currentFilter.getClass().equals(filter.getClass()));
        activeFilters.add(filter);
        filterCards();
    }

    public static void removeFilterType(CardFilter filter){
        activeFilters.removeIf(currentFilter -> currentFilter.getClass().equals(filter.getClass()));
        filterCards();
    }

    public static void clearFilters(){
        activeFilters.clear();
    }

    public static ArrayList<Card> filterCards(){
        ArrayList<Card> filteredCards = new ArrayList<>(allCards);
        ArrayList<Card> currentCards = new ArrayList<>();
        for (CardFilter filter : activeFilters){
            for (Card card : filteredCards){
                if (filter.matches(card)){
                    currentCards.add(card);
                }
            }
            filteredCards.clear();
            filteredCards.addAll(currentCards);
            currentCards.clear();

        }
        //System.out.print(card.getCode());
        return filteredCards;

    }

    public static void main(String[] args) throws CsvValidationException, IOException {
       /* addCardsFromCSV();
        addFilter(new EventFilter("Beam"));
        System.out.println();
        addFilter(new CategoryFilter("Beam"));*/
    }



}
