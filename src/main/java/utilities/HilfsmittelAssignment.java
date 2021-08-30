package utilities;

import model.Event;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class HilfsmittelAssignment {
    private int maxCapacity;
    private int inUse;
    private List<List<EventDuration>> reservationsList;

    public HilfsmittelAssignment(int maxCapacity) {
        this.reservationsList = new ArrayList<>();
        this.maxCapacity = maxCapacity;
        this.inUse = 0;
    }

    public int getInUse() {
        return inUse;
    }

    public void setInUse(int inUse) {
        this.inUse = inUse;
    }

    public int getMaxCapacity() {
        return this.maxCapacity;
    }

    public boolean setMaxCapacity(int amount) {
        if(amount < inUse) {
            return false;
        }
        this.maxCapacity = amount;
        return true;
    }

    public int getAvailable(Date start, Date end) {
        int out = this.maxCapacity - this.inUse;
        for(List<EventDuration> reservations : this.reservationsList) {
            boolean available = true;
            for(EventDuration duration : reservations) {
                if(duration.collidesWith(new EventDuration(start, end))) {
                    available = false;
                    break;
                }
            }
            if(available)
                out++;
        }
        return out;
    }

    public void remove(Date from, Date until) {
        ArrayList<List<EventDuration>> remove = new ArrayList<>();
        for(List<EventDuration> reservations : this.reservationsList) {
            EventDuration delete = null;
            for(EventDuration duration : reservations) {
                if(duration.getEnd().equals(until) && duration.getStart().equals(from)) {
                    delete = duration;
                }
            }
            if(delete != null)
                reservations.remove(delete);
            if(reservations.isEmpty()) {
                this.inUse--;
                remove.add(reservations);
            }
        }
        this.reservationsList.removeAll(remove);
    }

    public boolean reserveNuntil(int n, Date from, Date until) {
        if(n > maxCapacity)
            return false;
        int amountReserved = 0;
        List<Integer> update = new ArrayList<>();
        int add = 0;
        int count = 0;
        for(int i = 0; i < this.maxCapacity; i++) {
            if(i < this.inUse) {
                List<EventDuration> reservations = this.reservationsList.get(i);
                boolean available = true;
                for(EventDuration duration : reservations) {
                    if(new EventDuration(from, until).collidesWith(duration)) {
                        available = false;
                        count++;
                        break;
                    }
                }
                if(available) {
                    amountReserved++;
                    update.add(i);
                }
            } else {
                add++;
                amountReserved++;
            }
            if(amountReserved == n) {
                for(Integer idx : update) {
                    this.reservationsList.get(idx).add(new EventDuration(from, until));
                }
                for(int j = 0; j < add; j++) {
                    List<EventDuration> newReservations = new ArrayList<>();
                    newReservations.add(new EventDuration(from, until));
                    this.reservationsList.add(newReservations);
                }
                this.inUse += add;
                return true;
            }
        }
        return false;
    }
}
