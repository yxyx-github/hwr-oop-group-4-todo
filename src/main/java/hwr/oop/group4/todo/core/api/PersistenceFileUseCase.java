package hwr.oop.group4.todo.core.api;

import hwr.oop.group4.todo.core.TodoList;
import hwr.oop.group4.todo.persistence.configuration.FileAdapterConfiguration;

public interface PersistenceFileUseCase {

    TodoList load(FileAdapterConfiguration config);
    void save(TodoList todoList, FileAdapterConfiguration config);
}
