package persistenz;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import model.EntityManager;
import model.Event;
import model.EventElement;
import utilities.InterfaceAdapter;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.HashMap;

public class EventEntityManager implements EntityManager<Event> {

    private HashMap<String, Event> allElements;
    private Gson gson;

    public EventEntityManager() {
        this.allElements = new HashMap<>();
        this.gson = new GsonBuilder().setPrettyPrinting().registerTypeAdapter(EventElement.class, new InterfaceAdapter<EventElement>()).create();
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
        return allElements.getOrDefault(id, null);
    }

    @Override
    public void remove(Event instance) {
        allElements.remove(instance.getId());
    }

    @Override
    public Collection<Event> getAll() {
        return allElements.values();
    }

    @Override
    public void loadFromJson() throws IOException {
        String fileContent = Files.readString(Paths.get("events.json"));
        Event[] events = gson.fromJson(fileContent, Event[].class);
        for (Event event : events) {
            this.allElements.put(event.getId(), event);
        }
    }

    @Override
    public void saveToJson() throws IOException {
        String json = gson.toJson(allElements.values().toArray(new Event[0]), Event[].class);
        Files.writeString(Paths.get("events.json"), json);
    }
}
