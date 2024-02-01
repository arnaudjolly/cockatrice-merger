package net.jolly.cardsxmlmerger;

import net.jolly.cardsxmlmerger.model.Card;
import net.jolly.cardsxmlmerger.model.Extension;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class CardsXMLMergerTest {

	@Test
	void loadFile() throws Exception {
		Map<String, Card> cards = new HashMap<>();
		Map<String, Extension> sets = new HashMap<>();
		CardsXMLMerger.loadFile("src/test/resources/black_knight_5ED-M10-M11-MED-ME4.xml", cards, sets);
		assertEquals(1, cards.size(), "Nb cards");
		assertEquals(5, sets.size(), "Nb sets");
	}

	@Test
	void loadFiles() throws Exception {
		Map<String, Card> cards = new HashMap<>();
		Map<String, Extension> sets = new HashMap<>();
		String[] fileNames = new String[]{"src/test/resources/black_knight_5ED-M10-M11-MED-ME4.xml", "src/test/resources/black_white_knight_ABU.xml"};
		CardsXMLMerger.loadFiles(fileNames, cards, sets);
		assertEquals(2, cards.size(), "Nb cards");
		assertEquals(8, sets.size(), "Nb sets");

		Card c = cards.get("Black Knight");
		assertNotNull(c);
		assertEquals("http://gatherer.wizards.com/Handlers/Image.ashx?multiverseid=50&type=card",
		             c.getPicURLForSet("LEA"), "LEA picURL");
		assertEquals("http://gatherer.wizards.com/Handlers/Image.ashx?multiverseid=190540&type=card",
		             c.getPicURLForSet("M10"), "M10 picURL");

	}
}
