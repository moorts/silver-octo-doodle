package utilities;

import java.util.Date;

public class EventDuration {
    private Date start;
    private Date end;

    public EventDuration(Date start, Date end) {
        this.start = start;
        this.end = end;
    }

    public boolean collidesWith(EventDuration duration) {
        if(this.start.after(duration.getEnd())) {
            return false;
        }
        else return !this.end.before(duration.getStart());
    }

    public Date getStart() {
        return start;
    }

    public void setStart(Date start) {
        this.start = start;
    }

    public Date getEnd() {
        return end;
    }

    public void setEnd(Date end) {
        this.end = end;
    }
}
