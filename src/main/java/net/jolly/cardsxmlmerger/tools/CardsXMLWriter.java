package net.jolly.cardsxmlmerger.tools;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.xml.stream.XMLEventFactory;
import javax.xml.stream.XMLEventWriter;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;

import net.jolly.cardsxmlmerger.model.Card;
import net.jolly.cardsxmlmerger.model.Extension;
import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.log4j.lf5.viewer.categoryexplorer.CategoryExplorerLogRecordFilter;

public class CardsXMLWriter {

	private static final String	LONGNAME	  = "longname";
	private static final String	NAME	      = "name";
	private static final String	MANACOST	  = "manacost";
	private static final String	TYPE	      = "type";
	private static final String	PT	          = "pt";
	private static final String	TABLEROW	  = "tablerow";
	private static final String	TEXT	      = "text";
	private static final String	COLOR	      = "color";
	private static final String	SET	          = "set";
	private static final String	PIC_URL_KEY	  = "picURL";
	private static final String	PIC_URL_HQ	  = "picURLHq";
	private static final String	PIC_URL_ST	  = "picURLSt";

	private String	            fileName	  = "cards.fused.xml";

	public CardsXMLWriter() {
		super();
	}

	public CardsXMLWriter(String fileName) {
		super();
		this.fileName = fileName;
	}

	public void write(Map<String, Extension> sets, Map<String, Card> cards) throws XMLStreamException, IOException {

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

			addSimpleTag(NAME, card.getName(), writer, eventFactory);
			addSetTags(card, writer, eventFactory);
			addColorTags(card.getColors(), writer, eventFactory);
			addSimpleTag(MANACOST, card.getManacost(), writer, eventFactory);
			addSimpleTag(TYPE, card.getType(), writer, eventFactory);
			if (card.getPt() != null)
				addSimpleTag(PT, card.getPt(), writer, eventFactory);
			addSimpleTag(TABLEROW, "" + card.getTablerow(), writer, eventFactory);
			addSimpleTag(TEXT, card.getText(), writer, eventFactory);

			writer.add(eventFactory.createEndElement("", null, "card"));
		}
		writer.add(eventFactory.createEndElement("", null, "cards"));
	}

	private void writeSets(Map<String, Extension> extensions, XMLEventWriter writer, XMLEventFactory eventFactory) throws XMLStreamException {
		writer.add(eventFactory.createStartElement("", null, "sets"));
		for (Extension extension : extensions.values()) {
			writer.add(eventFactory.createStartElement("", null, "set"));
			addSimpleTag(NAME, extension.getName(), writer, eventFactory);
			addSimpleTag(LONGNAME, extension.getLongName(), writer, eventFactory);
			writer.add(eventFactory.createEndElement("", null, "set"));
		}
		writer.add(eventFactory.createEndElement("", null, "sets"));
	}

	private void addColorTags(Set<String> colors, XMLEventWriter writer, XMLEventFactory eventFactory) throws XMLStreamException {
		for (String color : colors) {
			addSimpleTag(COLOR, color, writer, eventFactory);
		}
	}

	private void addSetTags(Card card, XMLEventWriter writer, XMLEventFactory eventFactory) throws XMLStreamException {
		for (Entry<String, String> urlEntry: card.getPicURLForSets().entrySet()) {
			writer.add(eventFactory.createStartElement("", null, SET));
			writer.add(eventFactory.createAttribute(PIC_URL_KEY, urlEntry.getValue()));
			writer.add(eventFactory.createAttribute(PIC_URL_HQ, ""));
			writer.add(eventFactory.createAttribute(PIC_URL_ST, ""));
			writer.add(eventFactory.createCharacters(urlEntry.getKey()));
			writer.add(eventFactory.createEndElement("", null, SET));
		}

	}

	private void addSimpleTag(String tagName, String value, XMLEventWriter writer, XMLEventFactory eventFactory) throws XMLStreamException {
		writer.add(eventFactory.createStartElement("", null, tagName));
		writer.add(eventFactory.createCharacters(value));
		writer.add(eventFactory.createEndElement("", null, tagName));
	}
}
