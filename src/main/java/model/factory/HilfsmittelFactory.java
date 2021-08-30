package model.factory;

import model.Hilfsmittel;

import java.util.UUID;

public class HilfsmittelFactory {
    public Hilfsmittel createHilfsmittel(String name) {
        Hilfsmittel neuesHilfsmittel = new Hilfsmittel();
        neuesHilfsmittel.setId(UUID.randomUUID().toString());
        neuesHilfsmittel.setName(name);
        neuesHilfsmittel.setInsgesamtVerfuegbar(1);
        return neuesHilfsmittel;
    }
}
