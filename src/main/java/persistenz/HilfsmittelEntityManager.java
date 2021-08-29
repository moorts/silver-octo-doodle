package persistenz;

import com.google.gson.Gson;
import model.EntityManager;
import model.Hilfsmittel;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.HashMap;

public class HilfsmittelEntityManager implements EntityManager<Hilfsmittel> {
    private HashMap<String, Hilfsmittel> allElements;

    public HilfsmittelEntityManager() {
        this.allElements = new HashMap<>();
    }

    @Override
    public boolean contains(Hilfsmittel instance) {
        return this.allElements.containsKey(instance.getId());
    }

    @Override
    public void persist(Hilfsmittel instance) throws Exception {
        if(this.contains(instance))
            throw new Exception("Element already persisted");
        this.allElements.put(instance.getId(), instance);
    }

    @Override
    public Hilfsmittel find(String id) {
        return null;
    }

    @Override
    public void remove(Hilfsmittel instance) {
        this.allElements.remove(instance.getId());
    }

    @Override
    public Collection<Hilfsmittel> getAll() {
        return allElements.values();
    }

    @Override
    public void loadFromJson() throws IOException {
        Gson gson = new Gson();
        String fileContent = Files.readString(Paths.get("hilfsmittel.json"));
        Hilfsmittel[] hilfsmittel = gson.fromJson(fileContent, Hilfsmittel[].class);
        for (Hilfsmittel event : hilfsmittel) {
            this.allElements.put(event.getId(), event);
        }
    }

    @Override
    public void saveToJson() throws IOException {
        Gson gson = new Gson();
        String json = gson.toJson(allElements.values().toArray(new Hilfsmittel[0]), Hilfsmittel[].class);
        Files.writeString(Paths.get("hilfsmittel.json"), json);
    }
}