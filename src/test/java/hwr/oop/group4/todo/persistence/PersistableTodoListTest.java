package hwr.oop.group4.todo.persistence;

import hwr.oop.group4.todo.core.*;
import net.javacrumbs.jsonunit.core.Option;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

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

    @Test
    void canExportAsString() {
        TodoList todoList = getExampleTodoList();

        Persistable persistedTodoList = new PersistableTodoList(todoList);

        assertThatJson(persistedTodoList.exportAsString())
                .when(Option.IGNORING_ARRAY_ORDER)
                // TODO: use todolist.json
                .isEqualTo("{\"projects\":[{\"name\":\"project a\",\"description\":\"\",\"tags\":[],\"tasks\":[{\"name\":\"task c\",\"description\":\"description c\",\"priority\":0,\"tags\":[],\"status\":\"OPEN\"},{\"name\":\"task b\",\"description\":\"description b\",\"priority\":0,\"tags\":[],\"status\":\"OPEN\"}]}],\"inTray\":[{\"name\":\"idea a\",\"description\":\"description a\"},{\"name\":\"idea b\",\"description\":\"description b\"}],\"loseTasks\":[{\"name\":\"task a\",\"description\":\"description a\",\"priority\":0,\"deadline\":1063004940000,\"tags\":[{\"name\":\"tagA\"},{\"name\":\"tagB\"}],\"status\":\"OPEN\"}],\"somedayMaybe\":[]}");
    }

    @Test
    void canImportFromString() {
        String jsonString = "{\"projects\":[{\"name\":\"project a\",\"description\":\"\",\"tags\":[],\"tasks\":[{\"name\":\"task c\",\"description\":\"description c\",\"priority\":0,\"tags\":[],\"status\":\"OPEN\"},{\"name\":\"task b\",\"description\":\"description b\",\"priority\":0,\"tags\":[],\"status\":\"OPEN\"}]}],\"inTray\":[{\"name\":\"idea a\",\"description\":\"description a\"},{\"name\":\"idea b\",\"description\":\"description b\"}],\"loseTasks\":[{\"name\":\"task a\",\"description\":\"description a\",\"priority\":0,\"deadline\":1063004940000,\"tags\":[{\"name\":\"tagA\"},{\"name\":\"tagB\"}],\"status\":\"OPEN\"}],\"somedayMaybe\":[]}";

        PersistableTodoList persistableTodoList = new PersistableTodoList(new TodoList());
        persistableTodoList.importFromString(jsonString);

        assertThat(persistableTodoList.getTodoList()).isEqualTo(getExampleTodoList());
    }
}
