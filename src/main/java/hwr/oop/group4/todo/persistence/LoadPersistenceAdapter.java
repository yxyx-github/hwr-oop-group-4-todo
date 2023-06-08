package hwr.oop.group4.todo.persistence;

import hwr.oop.group4.todo.persistence.configuration.Configuration;

public interface LoadPersistenceAdapter<T> {

    T load(Persistable<T> persistable, Configuration config);
}
