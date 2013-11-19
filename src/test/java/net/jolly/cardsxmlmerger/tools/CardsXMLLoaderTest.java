package net.jolly.cardsxmlmerger.tools;

import net.jolly.cardsxmlmerger.model.Card;
import net.jolly.cardsxmlmerger.model.Extension;
import org.junit.Test;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class CardsXMLLoaderTest {

    @Test
    public void testLoad() throws Exception {
        InputStream stream = getClass().getClassLoader().getResourceAsStream("black_white_knight_ABU.xml");

        CardsXMLLoader xmlLoader = new CardsXMLLoader(stream);
        Map<String, Card> cards = new HashMap<String, Card>();
        Map<String, Extension> sets = new HashMap<String, Extension>();
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
