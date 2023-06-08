package hwr.oop.group4.todo.persistence;

import hwr.oop.group4.todo.persistence.configuration.Configuration;

public interface SavePersistenceAdapter {

    void save(Persistable data, Configuration config);
}
