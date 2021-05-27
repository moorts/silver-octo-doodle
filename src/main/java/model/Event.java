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

    private List<Kontaktinformationen> kontakte;

    private List<TeilEvent> teilEvents;

    public double summiereKosten() {
        return 0;
    }

    public String getId() {
        return this.id;
    }
}
