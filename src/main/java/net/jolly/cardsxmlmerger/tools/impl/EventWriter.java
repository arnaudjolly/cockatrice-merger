package net.jolly.cardsxmlmerger.tools.impl;

import net.jolly.cardsxmlmerger.model.Card;
import net.jolly.cardsxmlmerger.model.Extension;
import net.jolly.cardsxmlmerger.tools.CardsXMLWriter;

import javax.xml.stream.XMLEventFactory;
import javax.xml.stream.XMLEventWriter;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class EventWriter implements CardsXMLWriter {

	interface Attributes {
		String LONGNAME = "longname";
		String NAME = "name";
		String MANACOST = "manacost";
		String TYPE = "type";
		String PT = "pt";
		String TABLEROW = "tablerow";
		String TEXT = "text";
		String COLOR = "color";
		String SET = "set";
		String PIC_URL_KEY = "picURL";
		String PIC_URL_HQ = "picURLHq";
		String PIC_URL_ST = "picURLSt";
	}

	public EventWriter() {
		super();
	}

	@Override
	public void write(String fileName, Map<String, Extension> sets, Map<String, Card> cards) throws XMLStreamException, IOException {

		XMLOutputFactory outputFactory = XMLOutputFactory.newInstance();
		XMLEventWriter writer = outputFactory.createXMLEventWriter(new FileWriter(fileName));
		XMLEventFactory eventFactory = XMLEventFactory.newInstance();

		writer.add(eventFactory.createStartDocument());
		writer.add(eventFactory.createStartElement("", null, "cockatrice_carddatabase"));
		writer.add(eventFactory.createAttribute("version", "2"));
		writeSets(sets, writer, eventFactory);
		writeCards(cards, writer, eventFactory);
		writer.add(eventFactory.createEndElement("", null, "cockatrice_carddatabase"));
		writer.add(eventFactory.createEndDocument());

		writer.flush();
	}

	private void writeCards(Map<String, Card> cards, XMLEventWriter writer, XMLEventFactory eventFactory) throws XMLStreamException {
		writer.add(eventFactory.createStartElement("", null, "cards"));
		for (Card card : cards.values()) {
			writer.add(eventFactory.createStartElement("", null, "card"));

			addSimpleTag(Attributes.NAME, card.getName(), writer, eventFactory);
			addSetTags(card, writer, eventFactory);
			addColorTags(card.getColors(), writer, eventFactory);
			addSimpleTag(Attributes.MANACOST, card.getManacost(), writer, eventFactory);
			addSimpleTag(Attributes.TYPE, card.getType(), writer, eventFactory);
			if (card.getPt() != null)
				addSimpleTag(Attributes.PT, card.getPt(), writer, eventFactory);
			addSimpleTag(Attributes.TABLEROW, "" + card.getTablerow(), writer, eventFactory);
			addSimpleTag(Attributes.TEXT, card.getText(), writer, eventFactory);

			writer.add(eventFactory.createEndElement("", null, "card"));
		}
		writer.add(eventFactory.createEndElement("", null, "cards"));
	}

	private void writeSets(Map<String, Extension> extensions, XMLEventWriter writer, XMLEventFactory eventFactory) throws XMLStreamException {
		writer.add(eventFactory.createStartElement("", null, "sets"));
		for (Extension extension : extensions.values()) {
			writer.add(eventFactory.createStartElement("", null, "set"));
			addSimpleTag(Attributes.NAME, extension.getName(), writer, eventFactory);
			addSimpleTag(Attributes.LONGNAME, extension.getLongName(), writer, eventFactory);
			writer.add(eventFactory.createEndElement("", null, "set"));
		}
		writer.add(eventFactory.createEndElement("", null, "sets"));
	}

	private void addColorTags(Set<String> colors, XMLEventWriter writer, XMLEventFactory eventFactory) throws XMLStreamException {
		for (String color : colors) {
			addSimpleTag(Attributes.COLOR, color, writer, eventFactory);
		}
	}

	private void addSetTags(Card card, XMLEventWriter writer, XMLEventFactory eventFactory) throws XMLStreamException {
		for (Entry<String, String> urlEntry : card.getPicURLForSets().entrySet()) {
			writer.add(eventFactory.createStartElement("", null, Attributes.SET));
			writer.add(eventFactory.createAttribute(Attributes.PIC_URL_KEY, urlEntry.getValue()));
			writer.add(eventFactory.createAttribute(Attributes.PIC_URL_HQ, ""));
			writer.add(eventFactory.createAttribute(Attributes.PIC_URL_ST, ""));
			writer.add(eventFactory.createCharacters(urlEntry.getKey()));
			writer.add(eventFactory.createEndElement("", null, Attributes.SET));
		}

	}

	private void addSimpleTag(String tagName, String value, XMLEventWriter writer, XMLEventFactory eventFactory) throws XMLStreamException {
		writer.add(eventFactory.createStartElement("", null, tagName));
		writer.add(eventFactory.createCharacters(value));
		writer.add(eventFactory.createEndElement("", null, tagName));
	}
}
