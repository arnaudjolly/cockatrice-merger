package net.jolly.cardsxmlmerger.model;

import lombok.Data;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Data
public class Card {
    private String name;
    private Set<String> colors;
    private String manacost;
    private String type;
    private String pt;
    private int tablerow;
    private String text;
    private Map<String, String> picURLForSets;

    public Card() {
        super();
        this.colors = new HashSet<String>();
        this.picURLForSets = new HashMap<String, String>();
    }

    public void addPicURLForSet(String set, String url) {
        getPicURLForSets().put(set, url);
    }

    public void addAllPicURLForSet(Map<String, String> mapOfPics) {
        getPicURLForSets().putAll(mapOfPics);
    }

    public String getPicURLForSet(String set) {
        return getPicURLForSets().get(set);
    }

    public boolean addColor(String color) {
        return colors.add(color);
    }

}
