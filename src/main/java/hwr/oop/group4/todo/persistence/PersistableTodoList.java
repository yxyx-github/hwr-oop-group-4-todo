package hwr.oop.group4.todo.persistence;

import com.google.gson.*;
import hwr.oop.group4.todo.core.*;
import hwr.oop.group4.todo.persistence.json.adapters.serializers.DateTimeSerializer;

import java.time.LocalDateTime;

public class PersistableTodoList implements Persistable {
    private final TodoList todoList;

    public PersistableTodoList(TodoList todoList) {
        this.todoList = todoList;
    }

    public TodoList getTodoList() {
        return todoList;
    }

    @Override
    public String exportAsString() {
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(LocalDateTime.class, new DateTimeSerializer())
                .create();
        return gson.toJson(todoList);
    }

    @Override
    public void importFromString(String dataString) {

    }
}
