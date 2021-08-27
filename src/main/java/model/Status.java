package model;

public enum Status {
    ERSTELLT("Erstellt", "orange"),
    GEPLANT("Geplant", "orange"),
    INARBEIT("In Arbeit", "orange"),
    FERTIG("Fertig", "green"),
    STORNIERT("Storniert", "rot"),
    PAUSIERT("Pausiert", "rot");

    private String displayname;
    private String color;

    public String getDisplayname() {
        return displayname;
    }

    public String getColor() {
        return color;
    }

    Status(String displayname, String color) {
        this.displayname = displayname;
        this.color = color;
    }

    public String getHtmlString() {
        return String.format("<font color='%s'>%s</font>", color, displayname);
    }

    @Override
    public String toString() {
        return displayname;
    }
}
