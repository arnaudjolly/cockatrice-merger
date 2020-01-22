package net.jolly.cardsxmlmerger.model;

import java.util.HashSet;
import java.util.Set;

public class Extension {
    private String name;
    private String longName;
    private Set<Card> cards;

    public Extension() {
        super();
        this.cards = new HashSet<>();
    }

    public boolean addCard(Card c) {
        return cards.add(c);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLongName() {
        return longName;
    }

    public void setLongName(String longName) {
        this.longName = longName;
    }

    public Set<Card> getCards() {
        return cards;
    }
}