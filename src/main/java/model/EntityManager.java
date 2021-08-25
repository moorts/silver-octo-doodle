package model;

import java.io.IOException;
import java.util.Collection;

public interface EntityManager<T> {

    public boolean contains(T instance);

    public void persist(T instance) throws Exception;

    public T find(String id);

    public void remove(T instance);

    public Collection<T> getAll();

    public void loadFromJson() throws IOException;

    public void saveToJson() throws IOException;
}
