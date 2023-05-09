package hwr.oop.group4.todo.persistence;

import java.io.File;

public interface LoadPersistenceAdapter {

    Persistable load(Persistable data, File file);
}
