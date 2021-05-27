package model;

import java.util.Date;
import java.util.List;

public class TeilEvent {
    private String name;
    private String kategorie;
    private Date start;
    private Date end;

    private Status status;

    private EventElement eventElement;

    private List<Zuweisung> zuweisungen;
}
