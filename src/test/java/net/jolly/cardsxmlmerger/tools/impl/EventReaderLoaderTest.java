package net.jolly.cardsxmlmerger.tools.impl;

import net.jolly.cardsxmlmerger.model.Card;
import net.jolly.cardsxmlmerger.model.Extension;
import net.jolly.cardsxmlmerger.tools.CardsXMLLoader;
import org.junit.Test;

import java.io.InputStream;
import java.util.LinkedHashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class EventReaderLoaderTest {

    @Test
    public void testLoad() throws Exception {
        InputStream stream = getClass().getClassLoader().getResourceAsStream("black_white_knight_ABU.xml");

        CardsXMLLoader xmlLoader = new EventReaderLoader(stream);
        Map<String, Card> cards = new LinkedHashMap<>();
        Map<String, Extension> sets = new LinkedHashMap<>();
        xmlLoader.load(cards, sets);

        assertEquals(2, cards.size());

        Card c = cards.values().iterator().next();
        assertEquals("tablerow", 2, c.getTablerow());
        assertEquals("manacost", "BB", c.getManacost());
        assertEquals("type", "Creature - Human Knight", c.getType());
        assertEquals("pt", "2/2", c.getPt());
        assertEquals("name", "Black Knight", c.getName());
        assertEquals("sets", 3, c.getPicURLForSets().size());
    }

}
