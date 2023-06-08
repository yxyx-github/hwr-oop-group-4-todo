package hwr.oop.group4.todo.persistence;

import hwr.oop.group4.todo.persistence.configuration.FileAdapterConfiguration;

public interface SavePersistenceAdapter {

    void save(Persistable data, FileAdapterConfiguration config);
}
