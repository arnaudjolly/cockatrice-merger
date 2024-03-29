package net.jolly.cardsxmlmerger.tools.impl;

import net.jolly.cardsxmlmerger.model.Card;
import net.jolly.cardsxmlmerger.model.Extension;
import net.jolly.cardsxmlmerger.tools.CardsXMLLoader;

import javax.xml.namespace.QName;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.XMLEvent;
import java.io.InputStream;
import java.util.Map;

/**
 * I wanted to use the different APIs of StAX. This is the one that uses EventReader API (iterator type)
 */
public class EventReaderLoader implements CardsXMLLoader {

	private final InputStream is;

	public EventReaderLoader(InputStream is) {
		super();
		this.is = is;
	}

	@Override
	public void load(Map<String, Card> cards, Map<String, Extension> extensions) throws XMLStreamException {
		XMLInputFactory xmlif = XMLInputFactory.newInstance();
		XMLEventReader xmler = xmlif.createXMLEventReader(is);

		XMLEvent event;

		while (xmler.hasNext()) {
			event = xmler.nextEvent();
			if (event.isStartElement()) {
				String elementName = event.asStartElement().getName().getLocalPart();
				switch (elementName) {
					case "cards" -> filterCards(xmler, cards);
					case "sets" -> filterSets(xmler, extensions);
				}
			}
		}

		xmler.close();
	}

	/**
	 * Parse the &lt;cards/&gt; block of the cards.xml file
	 *
	 * @param xmler the event reader to continue the parsing inside the block
	 * @param cards the collection of cards to update
	 * @throws javax.xml.stream.XMLStreamException TODO complete this
	 */
	private void filterCards(XMLEventReader xmler, Map<String, Card> cards) throws XMLStreamException {
		XMLEvent event;
		Card card = new Card();
		while (xmler.hasNext()) {
			event = xmler.nextEvent();
			if (event.isCharacters()) {
				continue;
			}
			if (event.isStartElement()) {
				doStartElementInCardsTag(event, xmler, card);
			} else if (event.isEndElement()) {
				String elementName = event.asEndElement().getName().getLocalPart();
				if ("cards".equals(elementName)) {
					break;
				}
				if ("card".equals(elementName)) {
					Card c = cards.get(card.getName());
					if (c != null) {
						c.getPicURLForSets().putAll(card.getPicURLForSets());
					} else {
						cards.put(card.getName(), card);
					}
					card = new Card();
				}
			}
		}
	}

	/**
	 * Parse the &lt;sets/&gt; block of the cards.xml file
	 *
	 * @param xmler the event reader to continue the parsing inside the block
	 * @param sets  the collection of sets to update
	 * @throws javax.xml.stream.XMLStreamException when shit occurs
	 */
	private void filterSets(XMLEventReader xmler, Map<String, Extension> sets) throws XMLStreamException {
		XMLEvent event;
		Extension extension = new Extension();
		while (xmler.hasNext()) {
			event = xmler.nextEvent();
			if (event.isCharacters()) {
				continue;
			}
			if (event.isStartElement()) {
				doStartElementInSetsTag(event, xmler, extension);
			} else if (event.isEndElement()) {
				String elementName = event.asEndElement().getName().getLocalPart();
				if ("sets".equals(elementName)) {// end of sets. break
					break;
				}
				if ("set".equals(elementName)) {
					sets.put(extension.getName(), extension);
					extension = new Extension();
				}
			}
		}
	}

	private void doStartElementInCardsTag(XMLEvent event, XMLEventReader xmler, Card card) throws XMLStreamException {
		String elementName = event.asStartElement().getName().getLocalPart();
		switch (elementName) {
			case "name" -> storeCardName(xmler, card);
			case "set" -> {
				String picURL = event.asStartElement().getAttributeByName(new QName("picURL")).getValue();
				addSetToCard(xmler, card, picURL);
			}
			case "color" -> addColorToCard(xmler, card);
			case "manacost" -> storeManacost(xmler, card);
			case "type" -> storeType(xmler, card);
			case "text" -> storeText(xmler, card);
			case "tablerow" -> storeTableRow(xmler, card);
			case "pt" -> storePT(xmler, card);
		}
	}

	private void doStartElementInSetsTag(XMLEvent event, XMLEventReader xmler, Extension extension) throws XMLStreamException {
		String elementName = event.asStartElement().getName().getLocalPart();
		switch (elementName) {
			case "longname" -> storeLongName(xmler, extension);
			case "name" -> storeExtensionName(xmler, extension);
		}
	}

	private void addColorToCard(XMLEventReader xmler, Card card) throws XMLStreamException {
		XMLEvent event = xmler.nextEvent();
		String colorName = event.asCharacters().getData();
		card.addColor(colorName);
	}

	private void addSetToCard(XMLEventReader xmler, Card card, String picURL) throws XMLStreamException {
		XMLEvent event = xmler.nextEvent();
		String setName = event.asCharacters().getData();
		card.addPicURLForSet(setName, picURL);
	}

	private void storeCardName(XMLEventReader xmler, Card card) throws XMLStreamException {
		XMLEvent event = xmler.nextEvent();
		StringBuilder cardNameBuilder = new StringBuilder();
		while (event.isCharacters()) {
			cardNameBuilder.append(event.asCharacters().getData());
			event = xmler.nextEvent();
		}
		card.setName(cardNameBuilder.toString());
	}

	private void storeExtensionName(XMLEventReader xmler, Extension extension) throws XMLStreamException {
		XMLEvent event = xmler.nextEvent();
		extension.setName(event.asCharacters().getData());
	}

	private void storeLongName(XMLEventReader xmler, Extension extension) throws XMLStreamException {
		XMLEvent event = xmler.nextEvent();
		extension.setLongName(event.asCharacters().getData());
	}

	private void storeManacost(XMLEventReader xmler, Card card) throws XMLStreamException {
		XMLEvent event = xmler.nextEvent();
		if (event.isCharacters()) {
			card.setManacost(event.asCharacters().getData());
		} else if (event.isEndElement()) {
			card.setManacost("");
		}
	}

	private void storePT(XMLEventReader xmler, Card card) throws XMLStreamException {
		XMLEvent event = xmler.nextEvent();
		card.setPt(event.asCharacters().getData());
	}

	private void storeTableRow(XMLEventReader xmler, Card card) throws XMLStreamException {
		XMLEvent event = xmler.nextEvent();
		card.setTablerow(Integer.parseInt(event.asCharacters().getData()));
	}

	private void storeText(XMLEventReader xmler, Card card) throws XMLStreamException {
		XMLEvent event = xmler.nextEvent();
		if (event.isCharacters()) {
			StringBuilder textBuffer = new StringBuilder();
			while (event.isCharacters()) {
				textBuffer.append(event.asCharacters().getData());
				event = xmler.nextEvent();
			}
			card.setText(textBuffer.toString());
		} else if (event.isEndElement()) {
			card.setText("");
		}
	}

	private void storeType(XMLEventReader xmler, Card card) throws XMLStreamException {
		XMLEvent event = xmler.nextEvent();
		if (event.isCharacters()) {
			card.setType(event.asCharacters().getData());
		} else if (event.isEndElement()) {
			card.setType("");
		}
	}

}
