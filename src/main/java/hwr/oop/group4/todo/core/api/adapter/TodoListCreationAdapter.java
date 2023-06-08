package hwr.oop.group4.todo.core.api.adapter;

import hwr.oop.group4.todo.core.TodoList;
import hwr.oop.group4.todo.core.api.TodoListCreationUseCase;

public class TodoListCreationAdapter implements TodoListCreationUseCase {

    @Override
    public TodoList create() {
        return new TodoList();
    }
}
