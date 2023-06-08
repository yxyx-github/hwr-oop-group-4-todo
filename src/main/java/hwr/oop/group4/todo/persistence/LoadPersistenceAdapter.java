package hwr.oop.group4.todo.persistence;

import hwr.oop.group4.todo.persistence.configuration.Configuration;

public interface LoadPersistenceAdapter {

    Persistable load(Persistable data, Configuration config);
}
