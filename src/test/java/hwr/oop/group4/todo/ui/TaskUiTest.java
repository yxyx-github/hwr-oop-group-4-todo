package hwr.oop.group4.todo.ui;

import hwr.oop.group4.todo.core.*;
import hwr.oop.group4.todo.ui.controller.ConsoleController;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class TaskUiTest {

    private final String menuOutput = "[1m<==== Task Menu ====>[0m" + System.lineSeparator() +
            "List and Edit your Tasks." +  System.lineSeparator() +  System.lineSeparator() +
            "Commands: " +  System.lineSeparator() +
            "  list" +  System.lineSeparator() +
            "    List all tasks." +  System.lineSeparator() +
            "  new" +  System.lineSeparator() +
            "    Create a new task." +  System.lineSeparator() +
            "  edit" +  System.lineSeparator() +
            "    Edit the attributes of a project." +  System.lineSeparator() +
            "    -id <id>" +  System.lineSeparator() +
            "      ID of the task to be edited." +  System.lineSeparator() +
            "    -name <name>" +  System.lineSeparator() +
            "      Change the name of the task." +  System.lineSeparator() +
            "    -desc <desc>" +  System.lineSeparator() +
            "      Change the description of the task." +  System.lineSeparator() +
            "    -priority <priority>" +  System.lineSeparator() +
            "      Change the priority of the task." +  System.lineSeparator() +
            "    -deadline <deadline>" +  System.lineSeparator() +
            "      Change the deadline of the task." +  System.lineSeparator() +
            "    -addTags <tag> <tag>" +  System.lineSeparator() +
            "      Add a new tags." +  System.lineSeparator() +
            "    -removeTag <tag> <tag>" +  System.lineSeparator() +
            "      Remove tags." +  System.lineSeparator() +
            "  remove" +  System.lineSeparator() +
            "    Remove a task." +  System.lineSeparator() +
            "    -id <id>" +  System.lineSeparator() +
            "      ID of the task to be removed" +  System.lineSeparator() +
            "  removeAllDone" +  System.lineSeparator() +
            "    Remove all task that are done." +  System.lineSeparator() +
            "  complete" +  System.lineSeparator() +
            "    Mark a task as done." +  System.lineSeparator() +
            "    -id <id>" +  System.lineSeparator() +
            "      ID of the completed task." +  System.lineSeparator() +
            "  progress" +  System.lineSeparator() +
            "    Mark a task as in progress." +  System.lineSeparator() +
            "    -id <id>" +  System.lineSeparator() +
            "      ID of the task which is to be set to in progrss." +  System.lineSeparator() +
            "  open" +  System.lineSeparator() +
            "    Reopens a task." +  System.lineSeparator() +
            "    -id <id>" +  System.lineSeparator() +
            "      ID of the task which is to be set to open." +  System.lineSeparator() +
            "  back" +  System.lineSeparator() +
            "    Returns to the previous menu." +  System.lineSeparator() +
            "  help" +  System.lineSeparator() +
            "    Prints this Menu again.";

    private String retrieveResultFrom(OutputStream outputStream) {
        return outputStream.toString();
    }

    private InputStream createInputStreamForInput(String input) {
        byte[] inputInBytes = input.getBytes();
        return new ByteArrayInputStream(inputInBytes);
    }

    private Project getExampleProject() {
        final Task closedTaskA = new Task.TaskBuilder().name("name").addTags(new Tag("123"), new Tag("abc")).build();
        final Task closedTaskB = new Task.TaskBuilder().name("name").description("123").build();

        closedTaskA.closed();
        closedTaskB.closed();

        return new Project.ProjectBuilder()
                .addTask(new Task.TaskBuilder().name("name").description("desd").build())
                .addTask(new Task.TaskBuilder().deadline(
                                LocalDateTime.of(2000, 10, 22, 0, 0))
                        .description("desd").priority(10).build())
                .addTask(new Task.TaskBuilder().name("name").description("desd").build())
                .addTasks(closedTaskA, closedTaskB)
                .build();
    }

    @Test
    void menu() {
        final InputStream inputStream = createInputStreamForInput("back" + System.lineSeparator());
        final OutputStream outputStream = new ByteArrayOutputStream();

        final TaskUi ui = new TaskUi(new ConsoleController(outputStream, inputStream));
        ui.menu(getExampleProject(), List.of("projects", "1"));
        final String output = retrieveResultFrom(outputStream);
        assertThat(output).startsWith(menuOutput + System.lineSeparator() +
                "| ID | Name            | Description                    | Tags       | Deadline | Priority | Status     |" + System.lineSeparator() +
                "=========================================================================================================" + System.lineSeparator())
                .contains("|            name |                            123 |            |          |        0 |     CLOSED |" + System.lineSeparator())
                .contains("|    unnamed task |                           desd |            | 22.10.00 |       10 |       OPEN |" + System.lineSeparator())
                .contains("|            name |                                |   123, abc |          |        0 |     CLOSED |" + System.lineSeparator())
                .contains("|            name |                           desd |            |          |        0 |       OPEN |" + System.lineSeparator())
                .endsWith("projects/1/task:> ");
    }

    @Test
    void create() {
        final InputStream inputStream = createInputStreamForInput("new" + System.lineSeparator() +
                "NAME" + System.lineSeparator() +
                "description" + System.lineSeparator() +
                "1" + System.lineSeparator() +
                "10.12.2021" + System.lineSeparator() +
                "list" + System.lineSeparator() +
                "back" + System.lineSeparator());
        final OutputStream outputStream = new ByteArrayOutputStream();

        final TaskUi ui = new TaskUi(new ConsoleController(outputStream, inputStream));
        ui.menu(new TodoList());

        assertThat(retrieveResultFrom(outputStream)).isEqualTo(menuOutput + System.lineSeparator() +
                "| ID | Name            | Description                    | Tags       | Deadline | Priority | Status     |" + System.lineSeparator() +
                "=========================================================================================================" + System.lineSeparator() +
                "tasks:> " +
                "tasks/new/name:> " +
                "tasks/new/description:> " +
                "Enter a whole number: tasks/new/priority:> " +
                "Enter a date/time formatted as 'dd.mm.yyyy' or 'dd.mm.yyyy hh:mm': tasks/new/deadline:> " +
                "tasks:> " +
                "| ID | Name            | Description                    | Tags       | Deadline | Priority | Status     |" + System.lineSeparator() +
                "=========================================================================================================" + System.lineSeparator() +
                "|  0 |            NAME |                    description |            | 10.12.21 |        1 |       OPEN |" + System.lineSeparator() +
                "tasks:> ");
    }

    @Test
    void createFromIdea() {
        final InputStream inputStream = createInputStreamForInput("2" + System.lineSeparator() +
                "10.12.2021" + System.lineSeparator() +
                "back" + System.lineSeparator());
        final OutputStream outputStream = new ByteArrayOutputStream();

        final TaskUi ui = new TaskUi(new ConsoleController(outputStream, inputStream));
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
    void editWrongId() {
        final InputStream inputStream = createInputStreamForInput("edit -id 0 -name name" + System.lineSeparator() +
                "back" + System.lineSeparator());
        final OutputStream outputStream = new ByteArrayOutputStream();

        final TaskUi ui = new TaskUi(new ConsoleController(outputStream, inputStream));
        ui.menu(new TodoList());
        assertThat(retrieveResultFrom(outputStream)).isEqualTo(menuOutput + System.lineSeparator() +
                "| ID | Name            | Description                    | Tags       | Deadline | Priority | Status     |" + System.lineSeparator() +
                "=========================================================================================================" + System.lineSeparator() +
                "tasks:> ID parameter is invalid." + System.lineSeparator() +
                "tasks:> ");
    }

    @Test
    void editName() {
        final InputStream inputStream = createInputStreamForInput("edit -id 0 -name newName" + System.lineSeparator() +
                "back" + System.lineSeparator());
        final OutputStream outputStream = new ByteArrayOutputStream();

        final TaskUi ui = new TaskUi(new ConsoleController(outputStream, inputStream));
        final TodoList list = new TodoList();
        list.addLoseTask(new Task.TaskBuilder().name("test").build());
        ui.menu(list);

        assertThat(list.getLoseTasks()).contains(new Task.TaskBuilder().name("newName").build());
    }

    @Test
    void editPriority() {
        final InputStream inputStream = createInputStreamForInput("edit -id 0 -priority 100" + System.lineSeparator() +
                "back" + System.lineSeparator());
        final OutputStream outputStream = new ByteArrayOutputStream();

        final TaskUi ui = new TaskUi(new ConsoleController(outputStream, inputStream));
        final TodoList list = new TodoList();
        list.addLoseTask(new Task.TaskBuilder().priority(12).build());
        ui.menu(list);

        assertThat(list.getLoseTasks()).contains(new Task.TaskBuilder().priority(100).build());
    }

    @Test
    void editDesc() {
        final InputStream inputStream = createInputStreamForInput("edit -id 0 -desc newDesc" + System.lineSeparator() +
                "back" + System.lineSeparator());
        final OutputStream outputStream = new ByteArrayOutputStream();

        final TaskUi ui = new TaskUi(new ConsoleController(outputStream, inputStream));
        final TodoList list = new TodoList();
        list.addLoseTask(new Task.TaskBuilder().description("desc").build());
        ui.menu(list);

        assertThat(list.getLoseTasks()).contains(new Task.TaskBuilder().description("newDesc").build());
    }

    @Test
    void editDeadline() {
        final InputStream inputStream = createInputStreamForInput("edit -id 0 -deadline 10.12.2022" + System.lineSeparator() +
                "back" + System.lineSeparator());
        final OutputStream outputStream = new ByteArrayOutputStream();

        final TaskUi ui = new TaskUi(new ConsoleController(outputStream, inputStream));
        final TodoList list = new TodoList();
        list.addLoseTask(new Task.TaskBuilder()
                .deadline(LocalDateTime.of(2020, 10, 10, 0, 0)).build());
        ui.menu(list);

        assertThat(list.getLoseTasks()).contains(new Task.TaskBuilder()
                .deadline(LocalDateTime.of(2022, 12, 10, 0, 0)).build());
    }

    @Test
    void addTags() {
        final InputStream inputStream = createInputStreamForInput("edit -id 0 -addTags abc xyz" + System.lineSeparator() +
                "back" + System.lineSeparator());
        final OutputStream outputStream = new ByteArrayOutputStream();

        final TaskUi ui = new TaskUi(new ConsoleController(outputStream, inputStream));
        final TodoList list = new TodoList();
        list.addLoseTask(new Task.TaskBuilder().addTag(new Tag("tag")).build());
        ui.menu(list);

        assertThat(list.getLoseTasks()).contains(new Task.TaskBuilder()
                        .addTags(new Tag("tag"), new Tag("abc"), new Tag("xyz")).build());
    }

    @Test
    void removeTags() {
        final InputStream inputStream = createInputStreamForInput("edit -id 0 -removeTags abc xyz" + System.lineSeparator() +
                "back" + System.lineSeparator());
        final OutputStream outputStream = new ByteArrayOutputStream();

        final TaskUi ui = new TaskUi(new ConsoleController(outputStream, inputStream));
        final TodoList list = new TodoList();
        list.addLoseTask(new Task.TaskBuilder()
                .addTags(new Tag("tag"), new Tag("abc"), new Tag("xyz")).build());
        ui.menu(list);

        assertThat(list.getLoseTasks()).contains(new Task.TaskBuilder().addTag(new Tag("tag")).build());
    }

    @Test
    void removeTask() {
        final InputStream inputStream = createInputStreamForInput("remove -id 0" + System.lineSeparator() +
                "y" + System.lineSeparator() +
                "back" + System.lineSeparator());
        final OutputStream outputStream = new ByteArrayOutputStream();

        final TaskUi ui = new TaskUi(new ConsoleController(outputStream, inputStream));
        final TodoList list = new TodoList();
        list.addLoseTask(new Task.TaskBuilder().name("test").build());
        ui.menu(list);

        assertThat(list.getLoseTasks()).isEmpty();
    }

    @Test
    void removeAllDone() {
        final InputStream inputStream = createInputStreamForInput("removeAllDone" + System.lineSeparator() +
                "back" + System.lineSeparator());
        final OutputStream outputStream = new ByteArrayOutputStream();

        final TaskUi ui = new TaskUi(new ConsoleController(outputStream, inputStream));
        final Project project = getExampleProject();
        ui.menu(project, List.of(""));

        assertThat(project.getTasks()).hasSize(2);
    }

    @Test
    void open() {
        final InputStream inputStream = createInputStreamForInput("open -id 0" + System.lineSeparator() +
                "back" + System.lineSeparator());
        final OutputStream outputStream = new ByteArrayOutputStream();

        final TaskUi ui = new TaskUi(new ConsoleController(outputStream, inputStream));
        final TodoList list = new TodoList();
        final Task task = new Task.TaskBuilder().name("test").build();
        task.closed();
        list.addLoseTask(task);
        ui.menu(list);

        assertThat(list.getLoseTasks()).contains(new Task.TaskBuilder().name("test").build());
    }

    @Test
    void inProgress() {
        final InputStream inputStream = createInputStreamForInput("inProgress -id 0" + System.lineSeparator() +
                "back" + System.lineSeparator());
        final OutputStream outputStream = new ByteArrayOutputStream();

        final TaskUi ui = new TaskUi(new ConsoleController(outputStream, inputStream));
        final TodoList list = new TodoList();
        list.addLoseTask(new Task.TaskBuilder().name("test").build());
        ui.menu(list);

        final Task expected = new Task.TaskBuilder().name("test").build();
        expected.inProgress();
        assertThat(list.getLoseTasks()).contains(expected);
    }

    @Test
    void close() {
        final InputStream inputStream = createInputStreamForInput("complete -id 0" + System.lineSeparator() +
                "back" + System.lineSeparator());
        final OutputStream outputStream = new ByteArrayOutputStream();

        final TaskUi ui = new TaskUi(new ConsoleController(outputStream, inputStream));
        final TodoList list = new TodoList();
        list.addLoseTask(new Task.TaskBuilder().name("test").build());
        ui.menu(list);

        final Task expected = new Task.TaskBuilder().name("test").build();
        expected.closed();
        assertThat(list.getLoseTasks()).contains(expected);
    }
}