package hwr.oop.group4.todo.persistence;

import hwr.oop.group4.todo.commons.exceptions.PersistenceRuntimeException;
import hwr.oop.group4.todo.core.*;
import net.javacrumbs.jsonunit.core.Option;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import static net.javacrumbs.jsonunit.assertj.JsonAssertions.assertThatJson;
import static org.assertj.core.api.Assertions.assertThat;

class PersistableTodoListTest {

    TodoList getExampleTodoList() {
        TodoList todoList = new TodoList();

        Project projectA = new Project.ProjectBuilder()
                .name("project a")
                .build();

        Tag tagA = new Tag("tagA");
        Tag tagB = new Tag("tagB");
        Task taskA = new Task.TaskBuilder()
                .name("task a")
                .description("description a")
                .addTags(tagA, tagB)
                .deadline(LocalDateTime.of(2003, 9, 8, 7, 9))
                .build();
        Task taskB = new Task.TaskBuilder()
                .name("task b")
                .description("description b")
                .build();
        Task taskC = new Task.TaskBuilder()
                .name("task c")
                .description("description c")
                .build();

        projectA.addTask(taskB);
        projectA.addTask(taskC);

        Idea ideaA = new Idea("idea a", "description a");
        Idea ideaB = new Idea("idea b", "description b");

        todoList.addProject(projectA);
        todoList.addLoseTask(taskA);

        todoList.addIdea(ideaA);
        todoList.addIdea(ideaB);

        return todoList;
    }

    String getExampleJSON() {
        try (InputStream input = getClass().getClassLoader().getResourceAsStream("todolist.json");) {
            if (input == null) {
                return "";
            }
            return new String(input.readAllBytes(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new PersistenceRuntimeException("Failed reading json example", e);
        }
    }

    @Test
    void canExportAsString() {
        TodoList todoList = getExampleTodoList();

        Persistable persistedTodoList = new PersistableTodoList(todoList);

        assertThatJson(persistedTodoList.exportAsString())
                .when(Option.IGNORING_ARRAY_ORDER)
                .isEqualTo(getExampleJSON());
    }

    @Test
    void canImportFromString() {
        String jsonString = getExampleJSON();

        PersistableTodoList persistableTodoList = new PersistableTodoList(new TodoList());
        persistableTodoList.importFromString(jsonString);

        assertThat(persistableTodoList.getTodoList()).isEqualTo(getExampleTodoList());
    }
}
