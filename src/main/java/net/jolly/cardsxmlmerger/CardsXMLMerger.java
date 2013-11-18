package net.jolly.cardsxmlmerger;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import net.jolly.cardsxmlmerger.model.Card;
import net.jolly.cardsxmlmerger.model.Extension;
import net.jolly.cardsxmlmerger.tools.CardsXMLLoader;
import net.jolly.cardsxmlmerger.tools.CardsXMLWriter;
import org.apache.log4j.Logger;

import javax.xml.stream.XMLStreamException;

public class CardsXMLMerger {

    private static final Logger log = Logger.getLogger(CardsXMLMerger.class);


	public static void main(String[] args) throws Exception {
        if (args == null || args.length < 2) {
            log.error("Usage: ./cards-xml-merger.jar <file1> <file2> [<file3>...]");
            return;
        }

        Map<String, Card> cards = new HashMap<String, Card>();
        Map<String, Extension> sets = new HashMap<String, Extension>();

        loadFiles(args, cards, sets);

        log.info(String.format("A total of %d cards for %d sets. !", cards.size(), sets.size()));

        writeFile(cards, sets);
		log.info("Done!");
	}

    static void loadFiles(String[] fileNames, Map<String, Card> cards, Map<String, Extension> sets) throws XMLStreamException, IOException {
        for (int i = 0; i < fileNames.length; i++) {
            loadFile(fileNames[i], cards, sets);
        }
    }

    static void writeFile(Map<String, Card> cards, Map<String, Extension> sets) throws XMLStreamException, IOException {
        log.info("Writing file...");
        CardsXMLWriter xmlWriter = new CardsXMLWriter();
        xmlWriter.write(sets, cards);
    }

    static void loadFile(String fileName, Map<String, Card> cards, Map<String, Extension> sets) throws XMLStreamException, IOException {
        log.info(String.format("Reading file: '%s'...", fileName));
        FileInputStream fis1 = new FileInputStream(new File(fileName));
        CardsXMLLoader xmlLoader1 = new CardsXMLLoader(fis1);
        xmlLoader1.load(cards, sets);
    }

}
