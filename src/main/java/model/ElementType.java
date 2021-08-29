package model;

public enum ElementType {
    CATERING("Catering"),
    MUSIK("Musik"),
    LOCATION("Location");

    private String anzeigeText;

    ElementType(String anzeigeText) {
        this.anzeigeText = anzeigeText;
    }

    public String getAnzeigeText() {
        return anzeigeText;
    }

    @Override
    public String toString() {
        return anzeigeText;
    }
}
