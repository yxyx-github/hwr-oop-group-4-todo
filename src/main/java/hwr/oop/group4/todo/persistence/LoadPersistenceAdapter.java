package hwr.oop.group4.todo.persistence;

import hwr.oop.group4.todo.persistence.configuration.Configuration;

public interface LoadPersistenceAdapter<T> {

    Persistable<T> load(Persistable<T> data, Configuration config);
}
