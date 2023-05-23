package hwr.oop.group4.todo.persistence;

import com.google.gson.*;
import hwr.oop.group4.todo.core.*;

import java.lang.reflect.Type;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

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

    private static class DateTimeSerializer implements JsonSerializer<LocalDateTime> {

        @Override
        public JsonElement serialize(LocalDateTime src, Type typeOfSrc, JsonSerializationContext context) {
            return new JsonPrimitive(src.toInstant(ZoneOffset.UTC).toEpochMilli());
        }
    }

    private static class DateTimeDeserializer implements JsonDeserializer<LocalDateTime> {

        @Override
        public LocalDateTime deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
            Instant instant = Instant.ofEpochMilli(jsonElement.getAsLong());
            return LocalDateTime.ofInstant(instant, ZoneOffset.UTC);
        }
    }

    @Override
    public void importFromString(String dataString) {
        /*JSONObject todoListObject = new JSONObject(dataString);

        JSONArray projectsArray = todoListObject.getJSONArray("projects");
        JSONArray inTrayArray = todoListObject.getJSONArray("inTray");
        for (int i = 0; i < inTrayArray.length(); i++) {
            Idea idea = importIdea(inTrayArray.getJSONObject(i));
            todoList.addIdea(idea);
        }
        JSONArray loseTasksArray = todoListObject.getJSONArray("loseTasks");
        for (int i = 0; i < loseTasksArray.length(); i++) {
            Task task = importTask(loseTasksArray.getJSONObject(i));
            todoList.addLoseTask(task);
        }
        JSONArray somedayMaybeArray = todoListObject.getJSONArray("somedayMaybe");*/
    }

    /*private Task importTask(JSONObject taskObject) {
        return new Task.TaskBuilder()
                .name(taskObject.getString("name"))
                .description(taskObject.getString("description"))
                // TODO: deadline
                .priority(taskObject.getInt("priority"))
                // TODO: tags
                .status(taskObject.getEnum(Status.class, "status"))
                .build();
    }

    private Idea importIdea(JSONObject ideaObject) {
        return new Idea(
                ideaObject.getString("name"),
                ideaObject.getString("description")
        );
    }*/
}
