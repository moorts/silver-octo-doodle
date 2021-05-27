package model;

import java.util.HashMap;

public class EventEntityManager implements EntityManager<Event> {

    private HashMap<String, Event> allElements;

    public EventEntityManager() {
        this.allElements = new HashMap<>();
    }

    @Override
    public boolean contains(Event instance) {
        return allElements.containsKey(instance.getId());
    }

    @Override
    public void persist(Event instance) throws Exception {
        if(this.contains(instance))
            throw new Exception("Element already persisted");
        this.allElements.put(instance.getId(), instance);
    }

    @Override
    public Event find(String id) {
        return null;
    }

    @Override
    public void remove(Event instance) {

    }
}
