package edu.augustana;

import com.opencsv.exceptions.CsvValidationException;
import org.junit.jupiter.api.Test;
import edu.augustana.filters.*;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class CardDatabaseTest {
    private CardDatabase database;

    void setTestEnvironment() throws CsvValidationException, IOException {
        this.database = new CardDatabase();
        database.addCardsFromCSV();
    }

    @Test
    void filterCardsTest() throws CsvValidationException, IOException {
        setTestEnvironment();

        for (Card testCard : database.allCards) {
            //System.out.println(testCard);
            database.addFilter(new CategoryFilter(testCard.getCategory()));
            assertTrue(database.filterCards().contains(testCard));
            database.clearFilters();

            database.addFilter(new CodeFilter(testCard.getCode()));
            assertTrue(database.filterCards().contains(testCard));
            database.clearFilters();

            database.addFilter(new DifficultyFilter(testCard.getLevel().get(0)));
            assertTrue(database.filterCards().contains(testCard));
            database.clearFilters();

            database.addFilter(new EquipmentFilter(testCard.getEquipment().get(0)));
            assertTrue(database.filterCards().contains(testCard));
            database.clearFilters();

            database.addFilter(new EventFilter(testCard.getEvent()));
            assertTrue(database.filterCards().contains(testCard));
            database.clearFilters();

            database.addFilter(new GenderFilter(testCard.getGender()));
            assertTrue(database.filterCards().contains(testCard));
            database.clearFilters();

            database.addFilter(new TitleFilter(testCard.getTitle()));
            assertTrue(database.filterCards().contains(testCard));
            database.clearFilters();
        }

    }
}