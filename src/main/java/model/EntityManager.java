package model;

public interface EntityManager<T> {

    public boolean contains(T instance);

    public void persist(T instance) throws Exception;

    public T find(String id);

    public void remove(T instance);
}
