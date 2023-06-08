package hwr.oop.group4.todo.persistence;

import hwr.oop.group4.todo.persistence.configuration.Configuration;

public interface LoadPersistenceAdapter<T, C extends Configuration> {

    T load(Persistable<T> persistable, C config);
}
