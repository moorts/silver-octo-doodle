package model;

import java.util.List;

public class Hilfsmittel {
    private String id;
    private String name;
    private String beschreibung;
    private int insgesamtVerfuegbar;
    private int aktuellVerfuegbar;
    private List<String> tagList;
    private List<String> bilder;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBeschreibung() {
        return beschreibung;
    }

    public void setBeschreibung(String beschreibung) {
        this.beschreibung = beschreibung;
    }

    public List<String> getTagList() {
        return tagList;
    }

    public void setTagList(List<String> tagList) {
        this.tagList = tagList;
    }

    public int getInsgesamtVerfuegbar() {
        return insgesamtVerfuegbar;
    }

    public void setInsgesamtVerfuegbar(int insgesamtVerfuegbar) {
        this.insgesamtVerfuegbar = insgesamtVerfuegbar;
    }

    public int getAktuellVerfuegbar() {
        return aktuellVerfuegbar;
    }

    public void setAktuellVerfuegbar(int aktuellVerfuegbar) {
        this.aktuellVerfuegbar = aktuellVerfuegbar;
    }

    public List<String> getBilder() {
        return bilder;
    }

    public void setBilder(List<String> bilder) {
        this.bilder = bilder;
    }

    @Override
    public String toString() {
        return name + " (" + insgesamtVerfuegbar + ")";
    }
}
