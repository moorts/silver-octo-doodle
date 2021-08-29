package model.factory;

import model.Event;
import model.Status;
import persistenz.EventEntityManager;

import java.util.Date;
import java.util.UUID;

public class EventFactory {
    public Event createEvent(String titel) {
        Event newEvent = new Event();
        newEvent.setId(UUID.randomUUID().toString());
        newEvent.setTitel(titel);
        newEvent.setStart(new Date());
        newEvent.setEnde(new Date());
        newEvent.setStatus(Status.ERSTELLT);
        return newEvent;
    }
}
