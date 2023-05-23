package hwr.oop.group4.todo.core.api.adapter;

import hwr.oop.group4.todo.core.TodoList;
import hwr.oop.group4.todo.core.api.PersistenceFileUseCase;
import hwr.oop.group4.todo.persistence.LoadPersistenceAdapter;
import hwr.oop.group4.todo.persistence.PersistableTodoList;
import hwr.oop.group4.todo.persistence.SavePersistenceAdapter;

import java.io.File;

public class PersistenceAdapter implements PersistenceFileUseCase {

    final LoadPersistenceAdapter loadAdapter;
    final SavePersistenceAdapter saveAdapter;


    public PersistenceAdapter(LoadPersistenceAdapter loadAdapter, SavePersistenceAdapter saveAdapter) {
        this.loadAdapter = loadAdapter;
        this.saveAdapter = saveAdapter;
    }

    @Override
    public TodoList load(File file) {
        // waits for others
        return null;
    }

    @Override
    public void save(TodoList todoList, File file) {
        saveAdapter.save(new PersistableTodoList(todoList), file);
    }
}
