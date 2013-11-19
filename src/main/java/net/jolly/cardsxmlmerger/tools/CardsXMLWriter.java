package net.jolly.cardsxmlmerger.tools;

import net.jolly.cardsxmlmerger.model.Card;
import net.jolly.cardsxmlmerger.model.Extension;

import javax.xml.stream.XMLStreamException;
import java.io.IOException;
import java.util.Map;

public interface CardsXMLWriter {

    /**
     * Write a cards.xml file with name {@code fileName} that contains all cards and sets from collections.
     *
     * @param fileName the output filename
     * @param sets     the set collection
     * @param cards    the card collection
     * @throws XMLStreamException TODO complete this
     * @throws IOException        TODO complete this
     */
    void write(String fileName, Map<String, Extension> sets, Map<String, Card> cards) throws XMLStreamException, IOException;
}
