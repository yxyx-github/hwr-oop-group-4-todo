package hwr.oop.group4.todo.ui;

import hwr.oop.group4.todo.core.Project;
import hwr.oop.group4.todo.core.TodoList;
import hwr.oop.group4.todo.ui.controller.ConsoleController;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.time.LocalDateTime;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class SomeDayMaybeUiTest {

    private final String someDayMaybeMenuOutput =
            "[1m<==== SomeDayMaybe List ====>[0m" + System.lineSeparator() +
            "Manage your list of tasks you want to take care of!" + System.lineSeparator() +
            System.lineSeparator() +
            "Commands: " + System.lineSeparator() +
            "  list" + System.lineSeparator() +
            "  new" + System.lineSeparator() +
            "    Add something to the list." + System.lineSeparator() +
            "  remove" + System.lineSeparator() +
            "    Remove it from the list." + System.lineSeparator() +
            "    -id <id>" + System.lineSeparator() +
            "      ID of the list to be removed." + System.lineSeparator() +
            "  move" + System.lineSeparator() +
            "    Move it to projects." + System.lineSeparator() +
            "    -id <id>" + System.lineSeparator() +
            "      ID of the list to be used." + System.lineSeparator() +
            "  back" + System.lineSeparator() +
            "    Returns to the previous menu." + System.lineSeparator() +
            "someDayMaybe:> ";

    private final String listOutput =
            "| ID | Name            | Description     |" + System.lineSeparator() +
            "==========================================" + System.lineSeparator() +
            "|  0 |     TestProject |             Bla |" + System.lineSeparator() +
            "|  1 |             God |             2nd |" + System.lineSeparator();

    private String retrieveResultFrom(OutputStream outputStream) {
        return outputStream.toString();
    }

    private InputStream createInputStreamForInput(String input) {
        byte[] inputInBytes = input.getBytes();
        return new ByteArrayInputStream(inputInBytes);
    }

    private TodoList getExampleTodoList(boolean addExampleProjects) {
        TodoList todoList = new TodoList();

        todoList.addSomedayMaybeProject(new Project.ProjectBuilder()
                .name("TestProject")
                .description("Bla")
                .begin(LocalDateTime.of(2023,5,22,0,12))
                .end(LocalDateTime.of(2023,6,3,12,45))
                .build());

        todoList.addSomedayMaybeProject(new Project.ProjectBuilder()
                .name("God")
                .description("2nd")
                .begin(LocalDateTime.of(2003,12,20,6,5))
                .end(LocalDateTime.of(2003,12,20,6,45))
                .build());

        return todoList;
    }

    @Test
    void listSomeDayMaybe() {
        InputStream inputStream = createInputStreamForInput(
                "list" + System.lineSeparator() +
                "back" + System.lineSeparator());
        OutputStream outputStream = new ByteArrayOutputStream();

        SomeDayMaybeUi ui = new SomeDayMaybeUi(new ConsoleController(outputStream, inputStream));
        ui.menu(getExampleTodoList(true));

        String output = retrieveResultFrom(outputStream);

        assertThat(output).isEqualTo(
                someDayMaybeMenuOutput + listOutput + "someDayMaybe:> "
        );
    }

    @Test
    void newSomeDayMaybe() {
        InputStream inputStream = createInputStreamForInput(
                "new" + System.lineSeparator() +
                "Ha" + System.lineSeparator() +
                "Ho" + System.lineSeparator() +
                "back" + System.lineSeparator());
        OutputStream outputStream = new ByteArrayOutputStream();

        SomeDayMaybeUi ui = new SomeDayMaybeUi(new ConsoleController(outputStream, inputStream));
        ui.menu(getExampleTodoList(true));

        String output = retrieveResultFrom(outputStream);

        assertThat(output).isEqualTo(
                someDayMaybeMenuOutput +
                "project/new/name:> " +
                "project/new/description:> " +
                "someDayMaybe:> ");
    }

    @Test
    void removeSomeDayMaybe() {
        InputStream inputStream = createInputStreamForInput(
                "remove -id 0" + System.lineSeparator() +
                "y" + System.lineSeparator() +
                "back" + System.lineSeparator());
        OutputStream outputStream = new ByteArrayOutputStream();

        SomeDayMaybeUi ui = new SomeDayMaybeUi(new ConsoleController(outputStream, inputStream));
        ui.menu(getExampleTodoList(true));

        String output = retrieveResultFrom(outputStream);

        assertThat(output).isEqualTo(
                someDayMaybeMenuOutput +
                "Do you really want to remove TestProject?" + System.lineSeparator() +
                "Answer y/Y/yes or n/N/no (leave empty for: no)." + System.lineSeparator() +
                "projects/remove:> someDayMaybe:> ");
    }

    @Test
    void moveToProjects() {
        InputStream inputStream = createInputStreamForInput(
                "move id -0" + System.lineSeparator() +
                "back" + System.lineSeparator());
        OutputStream outputStream = new ByteArrayOutputStream();

        SomeDayMaybeUi ui = new SomeDayMaybeUi(new ConsoleController(outputStream, inputStream));
        ui.menu(getExampleTodoList(true));

        String output = retrieveResultFrom(outputStream);

        assertThat(output).isEqualTo(
                someDayMaybeMenuOutput +
                "someDayMaybe:> "
        );
    }
}
