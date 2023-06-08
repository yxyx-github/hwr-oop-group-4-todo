package hwr.oop.group4.todo.persistence;

import com.google.gson.*;
import hwr.oop.group4.todo.core.*;
import hwr.oop.group4.todo.persistence.json.adapters.deserializers.DateTimeDeserializer;
import hwr.oop.group4.todo.persistence.json.adapters.serializers.DateTimeSerializer;

import java.time.LocalDateTime;

public class PersistableTodoList implements Persistable<TodoList> {

    @Override
    public String exportAsString(TodoList data) {
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(LocalDateTime.class, new DateTimeSerializer())
                .create();
        return gson.toJson(data);
    }

    @Override
    public TodoList importFromString(String dataString) {
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(LocalDateTime.class, new DateTimeDeserializer())
                .create();

        return gson.fromJson(dataString, TodoList.class);
    }
}
