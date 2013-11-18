package net.jolly.cardsxmlmerger.model;

import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
public class Extension {
	private String	  name;
	private String	  longName;
	private Set<Card>	cards;

	public boolean addCard(Card c) {
		return cards.add(c);
	}

	public Extension() {
		super();
		this.cards = new HashSet<Card>();
	}

}