package hwr.oop.group4.todo.persistence;

import hwr.oop.group4.todo.persistence.configuration.Configuration;

public interface SavePersistenceAdapter<T, C extends Configuration> {

    void save(Persistable<T> persistable, T data, C config);
}
