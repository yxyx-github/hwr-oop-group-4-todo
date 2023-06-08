package hwr.oop.group4.todo.core.api.adapter;

import hwr.oop.group4.todo.commons.exceptions.PersistenceRuntimeException;
import hwr.oop.group4.todo.core.*;
import hwr.oop.group4.todo.core.api.PersistenceFileUseCase;
import hwr.oop.group4.todo.persistence.FileAdapter;
import hwr.oop.group4.todo.persistence.configuration.FileAdapterConfiguration;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

class PersistenceAdapterTest {

    TodoList getExampleTodoList() {
        final TodoList todoList = new TodoList();

        todoList.addProject(new Project.ProjectBuilder()
                .name("project a")
                .addTasks(new Task.TaskBuilder()
                                .name("task b")
                                .description("description b")
                                .build(),
                        new Task.TaskBuilder()
                                .name("task c")
                                .description("description c")
                                .build()
                )
                .build());
        todoList.addLoseTask(new Task.TaskBuilder()
                .name("task a")
                .description("description a")
                .addTags(new Tag("tagA"), new Tag("tagB"))
                .deadline(LocalDateTime.of(2003, 9, 8, 7, 9))
                .build());

        todoList.addIdea(new Idea("idea a", "description a"));
        todoList.addIdea(new Idea("idea b", "description b"));

        return todoList;
    }


    @Test
    void load() {
        final PersistenceFileUseCase useCase = new PersistenceAdapter(new FileAdapter(), new FileAdapter());
        final URL url = getClass().getClassLoader().getResource("core/api/adapter/todolist.json");
        if (url == null) {
            fail("Can't find file.");
        }
        try {
            final TodoList list = useCase.load(new FileAdapterConfiguration(new File(url.toURI())));
            assertThat(list).isEqualTo(getExampleTodoList());
        } catch (URISyntaxException e) {
            throw new PersistenceRuntimeException(e);
        }
    }

    @Test
    void save(@TempDir Path tempDir) {
        final PersistenceFileUseCase useCase = new PersistenceAdapter(new FileAdapter(), new FileAdapter());
        final File saveFile = new File(tempDir.toString() + "\\file.json");
        useCase.save(getExampleTodoList(), new FileAdapterConfiguration(saveFile));

        final TodoList list = useCase.load(new FileAdapterConfiguration(saveFile));
        assertThat(list).isEqualTo(getExampleTodoList());
    }

}