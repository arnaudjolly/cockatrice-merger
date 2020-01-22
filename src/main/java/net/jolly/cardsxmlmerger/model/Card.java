package net.jolly.cardsxmlmerger.model;


import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

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
        this.colors = new HashSet<>();
        this.picURLForSets = new HashMap<>();
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<String> getColors() {
        return colors;
    }

    public String getManacost() {
        return manacost;
    }

    public void setManacost(String manacost) {
        this.manacost = manacost;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPt() {
        return pt;
    }

    public void setPt(String pt) {
        this.pt = pt;
    }

    public int getTablerow() {
        return tablerow;
    }

    public void setTablerow(int tablerow) {
        this.tablerow = tablerow;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Map<String, String> getPicURLForSets() {
        return picURLForSets;
    }
}
