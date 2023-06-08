package hwr.oop.group4.todo.persistence;

import hwr.oop.group4.todo.persistence.configuration.FileAdapterConfiguration;

public interface LoadPersistenceAdapter {

    Persistable load(Persistable data, FileAdapterConfiguration config);
}
