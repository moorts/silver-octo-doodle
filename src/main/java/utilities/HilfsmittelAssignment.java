package utilities;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class HilfsmittelAssignment {
    private int maxCapacity;
    private int inUse;
    private List<Date> reservedUntil;

    public HilfsmittelAssignment(int maxCapacity) {
        this.reservedUntil = new ArrayList<>();
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

    public void remove(Date until) {
        ArrayList<Date> remove = new ArrayList<>();
        for(Date date : this.reservedUntil) {
            if(date.equals(until)) {
                remove.add(date);
                this.inUse--;
            }
        }
        this.reservedUntil.removeAll(remove);
    }

    public boolean reserveNuntil(int n, Date from, Date until) {
        if(n > maxCapacity)
            return false;
        int amountReserved = 0;
        List<Integer> update = new ArrayList<>();
        int add = 0;
        for(int i = 0; i < this.maxCapacity; i++) {
            if(i < this.inUse) {
                Date reserved = this.reservedUntil.get(i);
                if(reserved.before(from)) {
                    amountReserved++;
                    update.add(i);
                }
            } else {
                add++;
                amountReserved++;
            }
            if(amountReserved == n) {
                for(Integer idx : update) {
                    this.reservedUntil.set(idx, until);
                }
                for(int j = 0; j < add; j++)
                    this.reservedUntil.add(until);
                this.inUse += add;
                return true;
            }
        }
        return false;
    }
}
