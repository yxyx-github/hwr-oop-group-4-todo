package hwr.oop.group4.todo.core.api.adapter;

import hwr.oop.group4.todo.core.TodoList;
import hwr.oop.group4.todo.core.api.TodoListCreationUseCase;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;


class TodoListCreationAdapterTest {

    @Test
    void create() {
        final TodoListCreationUseCase creationUseCase = new TodoListCreationAdapter();
        final TodoList todoList = creationUseCase.create();

        assertThat(todoList).isNotNull();
    }
}