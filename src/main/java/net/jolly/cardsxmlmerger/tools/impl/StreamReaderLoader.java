package net.jolly.cardsxmlmerger.tools.impl;


import net.jolly.cardsxmlmerger.model.Card;
import net.jolly.cardsxmlmerger.model.Extension;
import net.jolly.cardsxmlmerger.tools.CardsXMLLoader;

import javax.xml.stream.XMLStreamException;
import java.io.IOException;
import java.util.Map;

/**
 * I wanted to use the different APIs of StAX. This is the one that uses StreamReader API. (cursor type)
 */
public class StreamReaderLoader implements CardsXMLLoader {

    @Override
    public void load(Map<String, Card> cards, Map<String, Extension> extensions) throws XMLStreamException, IOException {
        // TODO codes it!
    }
}
