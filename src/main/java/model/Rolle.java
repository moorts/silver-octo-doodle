package model;

import java.util.List;

public enum Rolle {
    Organisator,
    Beschaffungspersonal,
    Montageleiter,
    Personalmitarbeiter,
    Administrator;

    private List<String> berechtigungen;
}
