package net.jolly.cardsxmlmerger;

import net.jolly.cardsxmlmerger.model.Card;
import net.jolly.cardsxmlmerger.model.Extension;
import net.jolly.cardsxmlmerger.tools.CardsXMLLoader;
import net.jolly.cardsxmlmerger.tools.CardsXMLWriter;
import net.jolly.cardsxmlmerger.tools.impl.EventReaderLoader;
import net.jolly.cardsxmlmerger.tools.impl.EventWriter;
import org.apache.log4j.Logger;

import javax.xml.stream.XMLStreamException;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class CardsXMLMerger {

    private static final Logger log = Logger.getLogger(CardsXMLMerger.class);

    /**
     * Main method, entry point of the executable jar file.
     *
     * @param args paths of cards.xml files we want to merge
     * @throws Exception when shit occurs
     */
    public static void main(String[] args) throws Exception {
        if (args == null || args.length < 2) {
            log.error("Usage: ./cards-xml-merger.jar <file1> <file2> [<file3>...]");
            return;
        }

        Map<String, Card> cards = new HashMap<>();
        Map<String, Extension> sets = new HashMap<>();

        loadFiles(args, cards, sets);

        log.info(String.format("A total of %d cards for %d sets. !", cards.size(), sets.size()));

        writeFile(cards, sets);
        log.info("Done!");
    }

    /**
     * Read some files and store the cards in them into the map 'cards' and sets into the map 'sets'.
     *
     * @param fileNames the files we have to read
     * @param cards     the card collection we want to complete with cards referenced in those files
     * @param sets      the set collection we want to complete with sets referenced in those files.
     * @throws XMLStreamException in case a file is not well formed
     * @throws IOException        in case of there is a problem reading files.
     */
    static void loadFiles(String[] fileNames, Map<String, Card> cards, Map<String, Extension> sets) throws XMLStreamException, IOException {
        for (String filename : fileNames) {
            loadFile(filename, cards, sets);
        }
    }

    /**
     * Actions for writing a cards.xml file that can be read by Cockatrice as a database containing each card in cards and sets.
     *
     * @param cards the card collection we want to export in a file
     * @param sets  the set collection referenced in the card collection. (TODO: work to see if we can manage that an other way)
     * @throws XMLStreamException TODO: complete this
     * @throws IOException        TODO: complete this
     */
    static void writeFile(Map<String, Card> cards, Map<String, Extension> sets) throws XMLStreamException, IOException {
        log.info("Writing file...");
        //CardsXMLWriter xmlWriter = new EventWriter();
        CardsXMLWriter xmlWriter = new EventWriter();
        xmlWriter.write("cards.fused.xml", sets, cards);
    }

    /**
     * Read a file and store the cards and sets it references in collections cards and sets.
     *
     * @param fileName the file path you want to read
     * @param cards    the card collection we want to complete with cards referenced in those files
     * @param sets     the set collection we want to complete with sets referenced in those files.
     * @throws XMLStreamException in case of not well formed cards.xml file
     * @throws IOException        in case of problem reading the file.
     */
    static void loadFile(String fileName, Map<String, Card> cards, Map<String, Extension> sets) throws XMLStreamException, IOException {
        log.info(String.format("Reading file: '%s'...", fileName));
        FileInputStream fis = new FileInputStream(new File(fileName));
        //CardsXMLLoader xmlLoader1 = new StreamReaderLoader(fis);
        CardsXMLLoader xmlLoader1 = new EventReaderLoader(fis);
        xmlLoader1.load(cards, sets);
    }

}
