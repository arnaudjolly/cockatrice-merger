package net.jolly.cardsxmlmerger;

import net.jolly.cardsxmlmerger.model.Card;
import net.jolly.cardsxmlmerger.model.Extension;
import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class CardsXMLMergerTest {

    @Test
    public void testLoadFile() throws Exception {
        Map<String, Card> cards = new HashMap<>();
        Map<String, Extension> sets = new HashMap<>();
        CardsXMLMerger.loadFile("src/test/resources/black_knight_5ED-M10-M11-MED-ME4.xml", cards, sets);
        Assert.assertEquals("Nb cards", 1, cards.size());
        Assert.assertEquals("Nb sets", 5, sets.size());
    }

    @Test
    public void testLoadFiles() throws Exception {
        Map<String, Card> cards = new HashMap<>();
        Map<String, Extension> sets = new HashMap<>();
        String[] fileNames = new String[]{"src/test/resources/black_knight_5ED-M10-M11-MED-ME4.xml", "src/test/resources/black_white_knight_ABU.xml"};
        CardsXMLMerger.loadFiles(fileNames, cards, sets);
        Assert.assertEquals("Nb cards", 2, cards.size());
        Assert.assertEquals("Nb sets", 8, sets.size());

        Card c = cards.get("Black Knight");
        Assert.assertNotNull(c);
        Assert.assertEquals("LEA picURL", "http://gatherer.wizards.com/Handlers/Image.ashx?multiverseid=50&type=card", c.getPicURLForSet("LEA"));
        Assert.assertEquals("M10 picURL", "http://gatherer.wizards.com/Handlers/Image.ashx?multiverseid=190540&type=card", c.getPicURLForSet("M10"));

    }
}
