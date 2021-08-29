package model;

import java.util.Date;
import java.util.List;

public class Event {
    private String id;
    private String titel;
    private String beschreibung;
    private String kategorie;
    private Date start;
    private Date ende;
    private int erwarteteBesucherzahl;
    private String kunden;
    private double budget;
    private Status status;

    private List<String> bilder;
    private List<Kontaktinformationen> kontakte;
    private List<TeilEvent> teilEvents;

    public double summiereKosten() {
        return 0;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitel() {
        return titel;
    }

    public void setTitel(String titel) {
        this.titel = titel;
    }

    public String getBeschreibung() {
        return beschreibung;
    }

    public void setBeschreibung(String beschreibung) {
        this.beschreibung = beschreibung;
    }

    public String getKategorie() {
        return kategorie;
    }

    public void setKategorie(String kategorie) {
        this.kategorie = kategorie;
    }

    public Date getStart() {
        return start;
    }

    public void setStart(Date start) {
        this.start = start;
    }

    public Date getEnde() {
        return ende;
    }

    public void setEnde(Date ende) {
        this.ende = ende;
    }

    public int getErwarteteBesucherzahl() {
        return erwarteteBesucherzahl;
    }

    public void setErwarteteBesucherzahl(int erwarteteBesucherzahl) {
        this.erwarteteBesucherzahl = erwarteteBesucherzahl;
    }

    public String getKunden() {
        return kunden;
    }

    public void setKunden(String kunden) {
        this.kunden = kunden;
    }

    public double getBudget() {
        return budget;
    }

    public void setBudget(double budget) {
        this.budget = budget;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public List<String> getBilder() {
        return bilder;
    }

    public void setBilder(List<String> bilder) {
        this.bilder = bilder;
    }

    public List<Kontaktinformationen> getKontakte() {
        return kontakte;
    }

    public void setKontakte(List<Kontaktinformationen> kontakte) {
        this.kontakte = kontakte;
    }

    public List<TeilEvent> getTeilEvents() {
        return teilEvents;
    }

    public void setTeilEvents(List<TeilEvent> teilEvents) {
        this.teilEvents = teilEvents;
    }
}
