package net.jolly.cardsxmlmerger.tools;

import net.jolly.cardsxmlmerger.model.Card;
import net.jolly.cardsxmlmerger.model.Extension;

import javax.xml.stream.XMLStreamException;
import java.io.IOException;
import java.util.Map;

public interface CardsXMLLoader {

    /**
     * Read the input stream of a cards.xml file to store its content in cards and sets collections.
     *
     * @param cards      the collection of cards to update/complete
     * @param extensions the collection of sets to update/complete
     * @throws XMLStreamException TODO: complete this
     * @throws IOException        TODO: complete this
     */
    void load(Map<String, Card> cards, Map<String, Extension> extensions) throws XMLStreamException, IOException;

}
