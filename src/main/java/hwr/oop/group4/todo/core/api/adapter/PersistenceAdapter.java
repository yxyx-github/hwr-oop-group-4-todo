package hwr.oop.group4.todo.core.api.adapter;

import hwr.oop.group4.todo.commons.exceptions.PersistenceRuntimeException;
import hwr.oop.group4.todo.core.TodoList;
import hwr.oop.group4.todo.core.api.PersistenceFileUseCase;
import hwr.oop.group4.todo.persistence.LoadPersistenceAdapter;
import hwr.oop.group4.todo.persistence.Persistable;
import hwr.oop.group4.todo.persistence.PersistableTodoList;
import hwr.oop.group4.todo.persistence.SavePersistenceAdapter;
import hwr.oop.group4.todo.persistence.configuration.FileAdapterConfiguration;

import java.io.File;

public class PersistenceAdapter implements PersistenceFileUseCase {

    final LoadPersistenceAdapter loadAdapter;
    final SavePersistenceAdapter saveAdapter;


    public PersistenceAdapter(LoadPersistenceAdapter loadAdapter, SavePersistenceAdapter saveAdapter) {
        this.loadAdapter = loadAdapter;
        this.saveAdapter = saveAdapter;
    }

    @Override
    public TodoList load(FileAdapterConfiguration config) {
        Persistable persistable = loadAdapter.load(new PersistableTodoList(), config);
        if (persistable instanceof PersistableTodoList) {
            return ((PersistableTodoList) persistable).getTodoList();
        }
        throw new PersistenceRuntimeException("Expected a PersistableTodoList.");
    }

    @Override
    public void save(TodoList todoList, FileAdapterConfiguration config) {
        saveAdapter.save(new PersistableTodoList(todoList), config);
    }
}
