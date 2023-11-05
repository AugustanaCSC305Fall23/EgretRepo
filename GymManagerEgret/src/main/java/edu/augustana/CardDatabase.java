package edu.augustana;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.util.ArrayList;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvValidationException;
import edu.augustana.filters.CardFilter;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Label;


public class CardDatabase {

    public static ArrayList<Card> allCards = new ArrayList<>();
    //public static ArrayList<Card> filteredCards = allCards;
    public static ObservableList<CardFilter> activeFilters = FXCollections.observableArrayList();
    CardDatabase(){};

    public void addCardsFromCSV() throws IOException, CsvValidationException {
        //reads through the CSV file, skipping the headers in the first line.
        CSVReader reader = new CSVReaderBuilder(new FileReader("DEMO1.csv")).withSkipLines(1).build();
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
        System.out.println(allCards);
    }

    public static ArrayList<Card> getAllCards() {
        return allCards;
    }

    /*public void filterFloor(){
        filteredCards.removeIf(card -> !"Floor".equals(card.getEvent()));
    }*/

    public void addFilter(CardFilter filter){
        activeFilters.add(filter);
    }



}
