package hwr.oop.group4.todo.ui;

import hwr.oop.group4.todo.core.Idea;
import hwr.oop.group4.todo.core.Task;
import hwr.oop.group4.todo.ui.controller.ConsoleController;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class TaskCreationUiTest {

    private String retrieveResultFrom(OutputStream outputStream) {
        return outputStream.toString();
    }

    private InputStream createInputStreamForInput(String input) {
        byte[] inputInBytes = input.getBytes();
        return new ByteArrayInputStream(inputInBytes);
    }

    @Test
    void createFromIdea() {
        final InputStream inputStream = createInputStreamForInput("2" + System.lineSeparator() +
                "10.12.2021" + System.lineSeparator() +
                "back" + System.lineSeparator());
        final OutputStream outputStream = new ByteArrayOutputStream();

        final TaskCreationUi ui = new TaskCreationUi(new ConsoleController(outputStream, inputStream));
        final Idea idea = new Idea("taskName", "desc");
        Task task = ui.create(idea, List.of(""));
        assertThat(task).isEqualTo(new Task.TaskBuilder()
                .name("taskName")
                .description("desc")
                .priority(2)
                .deadline(LocalDateTime.of(2021, 12, 10, 0, 0))
                .build()
        );
    }

    @Test
    void createWithoutIdea() {
        final InputStream inputStream = createInputStreamForInput("abc" + System.lineSeparator() +
                "desc" + System.lineSeparator() +
                "2" + System.lineSeparator() +
                "10.12.2021" + System.lineSeparator() +
                "back" + System.lineSeparator());
        final OutputStream outputStream = new ByteArrayOutputStream();

        final TaskCreationUi ui = new TaskCreationUi(new ConsoleController(outputStream, inputStream));
        Task task = ui.create(null, List.of(""));
        assertThat(task).isEqualTo(new Task.TaskBuilder()
                .name("abc")
                .description("desc")
                .priority(2)
                .deadline(LocalDateTime.of(2021, 12, 10, 0, 0))
                .build()
        );
    }

}