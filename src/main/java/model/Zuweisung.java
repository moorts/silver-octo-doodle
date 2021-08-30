package model;

import persistenz.HilfsmittelEntityManager;

public class Zuweisung {
    private int menge;
    private String hilfsmittelId;

    private transient Hilfsmittel hilfsmittel;

    public int getMenge() {
        return menge;
    }

    public Hilfsmittel getHilfsmittel() {
        return hilfsmittel;
    }

    public void resolveId(HilfsmittelEntityManager entityManager) {
        this.hilfsmittel = entityManager.find(hilfsmittelId);
    }

    public void setMenge(int menge) {
        this.menge = menge;
    }

    public void setHilfsmittelId(String hilfsmittelId) {
        this.hilfsmittelId = hilfsmittelId;
    }
}
