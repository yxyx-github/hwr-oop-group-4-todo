package hwr.oop.group4.todo.core.api.adapter;

import hwr.oop.group4.todo.core.TodoList;
import hwr.oop.group4.todo.core.api.PersistenceFileUseCase;
import hwr.oop.group4.todo.persistence.LoadPersistenceAdapter;
import hwr.oop.group4.todo.persistence.PersistableTodoList;
import hwr.oop.group4.todo.persistence.SavePersistenceAdapter;
import hwr.oop.group4.todo.persistence.configuration.FileAdapterConfiguration;

public class PersistenceAdapter implements PersistenceFileUseCase {

    final LoadPersistenceAdapter<TodoList, FileAdapterConfiguration> loadAdapter;
    final SavePersistenceAdapter<TodoList, FileAdapterConfiguration> saveAdapter;


    public PersistenceAdapter(LoadPersistenceAdapter<TodoList, FileAdapterConfiguration> loadAdapter, SavePersistenceAdapter<TodoList, FileAdapterConfiguration> saveAdapter) {
        this.loadAdapter = loadAdapter;
        this.saveAdapter = saveAdapter;
    }

    @Override
    public TodoList load(FileAdapterConfiguration config) {
        return loadAdapter.load(new PersistableTodoList(), config);
    }

    @Override
    public void save(TodoList todoList, FileAdapterConfiguration config) {
        saveAdapter.save(new PersistableTodoList(), todoList, config);
    }
}
