package net.jolly.cardsxmlmerger.tools.impl;

import net.jolly.cardsxmlmerger.model.Card;
import net.jolly.cardsxmlmerger.model.Extension;
import net.jolly.cardsxmlmerger.tools.CardsXMLLoader;
import org.junit.jupiter.api.Test;

import java.io.InputStream;
import java.util.LinkedHashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

class EventReaderLoaderTest {

	@Test
	void load() throws Exception {
		InputStream stream = getClass().getClassLoader().getResourceAsStream("black_white_knight_ABU.xml");

		CardsXMLLoader xmlLoader = new EventReaderLoader(stream);
		Map<String, Card> cards = new LinkedHashMap<>();
		Map<String, Extension> sets = new LinkedHashMap<>();
		xmlLoader.load(cards, sets);

		assertEquals(2, cards.size());

		Card c = cards.values().iterator().next();
		assertEquals(2, c.getTablerow(), "tablerow");
		assertEquals("BB", c.getManacost(), "manacost");
		assertEquals("Creature - Human Knight", c.getType(), "type");
		assertEquals("2/2", c.getPt(), "pt");
		assertEquals("Black Knight", c.getName(), "name");
		assertEquals(3, c.getPicURLForSets().size(), "sets");
	}

}
