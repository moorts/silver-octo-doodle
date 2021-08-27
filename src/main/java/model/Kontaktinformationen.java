package model;

public class Kontaktinformationen {
    private String name;
    private String email;
    private String telnr;

    public String getName() {
        return name;
    }

    public Kontaktinformationen(String name, String email, String telnr) {
        this.name = name;
        this.email = email;
        this.telnr = telnr;
    }

    public String getEmail() {
        return email;
    }

    public String getTelnr() {
        return telnr;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setTelnr(String telnr) {
        this.telnr = telnr;
    }
}
