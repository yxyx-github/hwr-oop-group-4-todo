package hwr.oop.group4.todo.persistence;

import hwr.oop.group4.todo.persistence.configuration.Configuration;

public interface SavePersistenceAdapter<T> {

    void save(Persistable<T> data, Configuration config);
}
