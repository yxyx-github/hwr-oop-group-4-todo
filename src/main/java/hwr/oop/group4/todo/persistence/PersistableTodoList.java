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
