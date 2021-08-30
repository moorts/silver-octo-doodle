package utilities;

import model.Event;
import model.Hilfsmittel;
import model.Zuweisung;
import persistenz.HilfsmittelEntityManager;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.PriorityQueue;

public class HilfsmittelManagement {

    private HashMap<String, HilfsmittelAssignment> currentAssignments;
    private HilfsmittelEntityManager entities;

    public HilfsmittelManagement(HilfsmittelEntityManager entities) {
        this.currentAssignments = new HashMap<>();
        this.entities = entities;
    }

    public void loadEvent(Event e) {
        Date endDate = e.getEnde();
        for(Zuweisung z : e.getZuweisungen()) {
            z.resolveId(entities);
            Hilfsmittel h = z.getHilfsmittel();
            int amount = z.getMenge();
            loadHilfsmittel(h.getId(), amount, e.getStart(), e.getEnde());
        }
    }

    public void updateHilfsmittel(Date start, Date end) {
        for(Hilfsmittel h : entities.getAll()) {
            if(currentAssignments.containsKey(h.getId()))
                h.setAktuellVerfuegbar(currentAssignments.get(h.getId()).getAvailable(start, end));
        }
    }

    public void removeHilfsmittel(String id, Date startDate, Date endDate) {
        HilfsmittelAssignment assignment = this.currentAssignments.get(id);
        Hilfsmittel h = this.entities.find(id);
        assignment.remove(startDate, endDate);
        h.setAktuellVerfuegbar(h.getInsgesamtVerfuegbar() - assignment.getInUse());
    }

    public void loadHilfsmittel(String id, int amount, Date startDate, Date endDate) {
        Hilfsmittel hilfsmittel = this.entities.find(id);
        if(hilfsmittel == null)
            return;

        if(currentAssignments.containsKey(id)) {
            HilfsmittelAssignment assignments = currentAssignments.get(id);
            assignments.reserveNuntil(amount, startDate, endDate);
            hilfsmittel.setAktuellVerfuegbar(assignments.getAvailable(startDate, endDate));
            return;
        }
        HilfsmittelAssignment newAssignment = new HilfsmittelAssignment(hilfsmittel.getInsgesamtVerfuegbar());
        currentAssignments.put(id, newAssignment);
        newAssignment.reserveNuntil(amount, startDate, endDate);
        hilfsmittel.setAktuellVerfuegbar(hilfsmittel.getAktuellVerfuegbar() - amount);
    }
    public boolean reserveHilfsmittel(String id, int amount, Date startDate, Date endDate) {
        Hilfsmittel hilfsmittel = this.entities.find(id);
        if(hilfsmittel == null)
            return false;

        if(currentAssignments.containsKey(id)) {
            HilfsmittelAssignment assignments = currentAssignments.get(id);
            if(assignments.reserveNuntil(amount, startDate, endDate)) {
                hilfsmittel.setAktuellVerfuegbar(assignments.getAvailable(startDate, endDate));
                return true;
            }
            return false;
        }
        HilfsmittelAssignment newAssignment = new HilfsmittelAssignment(hilfsmittel.getInsgesamtVerfuegbar());
        currentAssignments.put(id, newAssignment);
        if(newAssignment.reserveNuntil(amount, startDate, endDate)) {
            hilfsmittel.setAktuellVerfuegbar(hilfsmittel.getAktuellVerfuegbar() - amount);
            return true;
        }
        return false;
    }
}
