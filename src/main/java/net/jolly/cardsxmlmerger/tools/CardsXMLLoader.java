package net.jolly.cardsxmlmerger.tools;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import javax.xml.namespace.QName;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.XMLEvent;

import net.jolly.cardsxmlmerger.model.Card;
import net.jolly.cardsxmlmerger.model.Extension;

public class CardsXMLLoader {

	private InputStream	is;

    public void load(Map<String, Card> cards, Map<String, Extension> extensions) throws XMLStreamException, IOException {
		XMLInputFactory xmlif = XMLInputFactory.newInstance();
		XMLEventReader xmler = xmlif.createXMLEventReader(is);

		XMLEvent event;

		while (xmler.hasNext()) {
			event = xmler.nextEvent();
			if (event.isStartElement()) {
				String elementName = event.asStartElement().getName().getLocalPart();
				if ("cards".equals(elementName)){
                   filterCards(xmler, cards);
                } else if ("sets".equals(elementName)) {
                    filterSets(xmler, extensions);
                }
			}
		}

		xmler.close();
		return;
	}

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
				if ("card".equals(elementName)) {
                    Card c = cards.get(card.getName());
                    if (c != null) {
                        c.getPicURLForSets().putAll(card.getPicURLForSets());
                    } else {
                        cards.put(card.getName(), card);
                    }
					card = new Card();
				} else if ("cards".equals(elementName)) {
					break;
				}
			}
		}
	}

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
				if ("set".equals(elementName)) {
					sets.put(extension.getName(), extension);
					extension = new Extension();
				} else if ("sets".equals(elementName)) {
					// end of sets. break
					break;
				}
			}
		}
	}

	private void doStartElementInCardsTag(XMLEvent event, XMLEventReader xmler, Card card) throws XMLStreamException {
	    String elementName = event.asStartElement().getName().getLocalPart();
	    if ("name".equals(elementName)) {
	    	storeCardName(xmler, card);
	    } else if ("set".equals(elementName)) {
            String picURL = event.asStartElement().getAttributeByName(new QName("picURL")).getValue();
	    	addSetToCard(xmler, card, picURL);
	    } else if ("color".equals(elementName)) {
	    	addColorToCard(xmler, card);
	    } else if ("manacost".equals(elementName)) {
	    	storeManacost(xmler, card);
	    } else if ("type".equals(elementName)) {
	    	storeType(xmler, card);
	    } else if ("text".equals(elementName)) {
	    	storeText(xmler, card);
	    } else if ("tablerow".equals(elementName)) {
	    	storeTableRow(xmler, card);
	    } else if ("pt".equals(elementName)) {
	    	storePT(xmler, card);
	    }
	}

	private void doStartElementInSetsTag(XMLEvent event, XMLEventReader xmler, Extension extension) throws XMLStreamException {
	    String elementName = event.asStartElement().getName().getLocalPart();
	    if ("longname".equals(elementName)) {
	    	storeLongName(xmler, extension);
	    } else if ("name".equals(elementName)) {
	    	storeExtensionName(xmler, extension);
	    }
    }

	private void addColorToCard(XMLEventReader xmler, Card card) throws XMLStreamException {
	    XMLEvent event;
	    event = xmler.nextEvent();
	    String colorName = event.asCharacters().getData();
	    card.addColor(colorName);
	}

	private void addSetToCard(XMLEventReader xmler, Card card, String picURL) throws XMLStreamException {
	    XMLEvent event;
	    event = xmler.nextEvent();
	    String setName = event.asCharacters().getData();
        card.addPicURLForSet(setName, picURL);
	}

	private void storeCardName(XMLEventReader xmler, Card card) throws XMLStreamException {
	    XMLEvent event;
	    event = xmler.nextEvent();
	    StringBuilder cardNameBuilder = new StringBuilder();
	    while (event.isCharacters()) {
	    	cardNameBuilder.append(event.asCharacters().getData());
	    	event = xmler.nextEvent();
	    }
	    card.setName(cardNameBuilder.toString());
	}

	private void storeExtensionName(XMLEventReader xmler, Extension extension) throws XMLStreamException {
	    XMLEvent event;
	    event = xmler.nextEvent();
	    extension.setName(event.asCharacters().getData());
    }

	private void storeLongName(XMLEventReader xmler, Extension extension) throws XMLStreamException {
	    XMLEvent event;
	    event = xmler.nextEvent();
	    extension.setLongName(event.asCharacters().getData());
    }

	private void storeManacost(XMLEventReader xmler, Card card) throws XMLStreamException {
	    XMLEvent event;
	    event = xmler.nextEvent();
	    if (event.isCharacters()) {
	    	card.setManacost(event.asCharacters().getData());
	    } else if (event.isEndElement()) {
	    	card.setManacost("");
	    }
	}

	private void storePT(XMLEventReader xmler, Card card) throws XMLStreamException {
	    XMLEvent event;
	    event = xmler.nextEvent();
	    card.setPt(event.asCharacters().getData());
	}

	private void storeTableRow(XMLEventReader xmler, Card card) throws XMLStreamException {
	    XMLEvent event;
	    event = xmler.nextEvent();
	    card.setTablerow(Integer.parseInt(event.asCharacters().getData()));
	}

	private void storeText(XMLEventReader xmler, Card card) throws XMLStreamException {
	    XMLEvent event;
	    event = xmler.nextEvent();
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
	    XMLEvent event;
	    event = xmler.nextEvent();
	    if (event.isCharacters()) {
	    	card.setType(event.asCharacters().getData());
	    } else if (event.isEndElement()) {
	    	card.setType("");
	    }
	}

	public CardsXMLLoader(InputStream is) throws XMLStreamException {
		super();
		this.is = is;
	}

}
