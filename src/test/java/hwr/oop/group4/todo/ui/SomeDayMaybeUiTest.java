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
            "    Returns to the previous menu." + System.lineSeparator();

    private final String listHeader =
            "| ID | Name            | Description     |" + System.lineSeparator() +
            "==========================================" + System.lineSeparator();
            //"|  0 |     TestProject |             Bla |" + System.lineSeparator() +
            //"|  1 |             God |             2nd |" + System.lineSeparator()

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
                someDayMaybeMenuOutput +
                listHeader +
                "|  0 |     TestProject |             Bla |" + System.lineSeparator() +
                "|  1 |             God |             2nd |" + System.lineSeparator() +
                "someDayMaybe:> " +
                listHeader +
                "|  0 |     TestProject |             Bla |" + System.lineSeparator() +
                "|  1 |             God |             2nd |" + System.lineSeparator() +
                listHeader +
                "|  0 |     TestProject |             Bla |" + System.lineSeparator() +
                "|  1 |             God |             2nd |" + System.lineSeparator() +
                "someDayMaybe:> "
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
                listHeader +
                "|  0 |     TestProject |             Bla |" + System.lineSeparator() +
                "|  1 |             God |             2nd |" + System.lineSeparator() +
                "someDayMaybe:> " +
                "project/new/name:> " +
                "project/new/description:> " +
                listHeader +
                "|  0 |     TestProject |             Bla |" + System.lineSeparator() +
                "|  1 |             God |             2nd |" + System.lineSeparator() +
                "|  2 |              Ha |              Ho |" + System.lineSeparator() +
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
                listHeader +
                "|  0 |     TestProject |             Bla |" + System.lineSeparator() +
                "|  1 |             God |             2nd |" + System.lineSeparator() +
                "someDayMaybe:> " +
                "Do you really want to remove TestProject?" + System.lineSeparator() +
                "Answer y/Y/yes or n/N/no (leave empty for: no)." + System.lineSeparator() +
                "projects/remove:> " +
                listHeader +
                "|  0 |             God |             2nd |" + System.lineSeparator() +
                "someDayMaybe:> ");
    }

    @Test
    void moveToProjects() {
        InputStream inputStream = createInputStreamForInput(
                "move -id 0" + System.lineSeparator() +
                "20.12.2003" + System.lineSeparator() +
                "20.12.2003" + System.lineSeparator() +
                "back" + System.lineSeparator());
        OutputStream outputStream = new ByteArrayOutputStream();

        SomeDayMaybeUi ui = new SomeDayMaybeUi(new ConsoleController(outputStream, inputStream));
        ui.menu(getExampleTodoList(true));

        String output = retrieveResultFrom(outputStream);

        assertThat(output).isEqualTo(
                someDayMaybeMenuOutput +
                listHeader +
                "|  0 |     TestProject |             Bla |" + System.lineSeparator() +
                "|  1 |             God |             2nd |" + System.lineSeparator() +
                "someDayMaybe:> " +
                "Enter a date/time formatted as 'dd.mm.yyyy' or 'dd.mm.yyyy hh:mm': " +
                "projects/new/begin:> " +
                "Enter a date/time formatted as 'dd.mm.yyyy' or 'dd.mm.yyyy hh:mm': " +
                "projects/new/end:> " +
                listHeader +
                "|  0 |             God |             2nd |" + System.lineSeparator() +
                "someDayMaybe:> "
        );
    }
    private final String projectsMenuOutput =
            "[1m<==== Project Menu ====>[0m" + System.lineSeparator() +
            "Manage your Projects!" + System.lineSeparator() +
            System.lineSeparator() +
            "Commands: " + System.lineSeparator() +
            "  list" + System.lineSeparator() +
            "    List all projects." + System.lineSeparator() +
            "  new" + System.lineSeparator() +
            "    Add a new project." + System.lineSeparator() +
            "  tasks" + System.lineSeparator() +
            "    Open the task menu for a project." + System.lineSeparator() +
            "    -id <id>" + System.lineSeparator() +
            "      ID of the project." + System.lineSeparator() +
            "  edit" + System.lineSeparator() +
            "    Edit the attributes of a project." + System.lineSeparator() +
            "    -id <id>" + System.lineSeparator() +
            "      ID of the project to be edited." + System.lineSeparator() +
            "    -name <name>" + System.lineSeparator() +
            "      Change the name of the project." + System.lineSeparator() +
            "    -desc <desc>" + System.lineSeparator() +
            "      Change the description of the project." + System.lineSeparator() +
            "    -begin" + System.lineSeparator() +
            "      Change the beginning of the project." + System.lineSeparator() +
            "    -end" + System.lineSeparator() +
            "      Change the end of the project" + System.lineSeparator() +
            "    -addTags <tag> [<tag> ...]" + System.lineSeparator() +
            "      Add a new tag." + System.lineSeparator() +
            "    -removeTags <tag> [<tag> ...]" + System.lineSeparator() +
            "      Remove a tag." + System.lineSeparator() +
            "  remove" + System.lineSeparator() +
            "    Remove a project." + System.lineSeparator() +
            "    -id <id>" + System.lineSeparator() +
            "      ID of the project to be removed." + System.lineSeparator() +
            "  back" + System.lineSeparator() +
            "    Returns to the previous menu." + System.lineSeparator() +
            "projects:> ";

    @Test
    void showProjectList() {
        InputStream inputStream = createInputStreamForInput(
                "" + System.lineSeparator() +
                "back" + System.lineSeparator()
        );
        OutputStream outputStream = new ByteArrayOutputStream();

        ProjectUi ui = new ProjectUi(new ConsoleController(outputStream, inputStream));
        ui.menu(getExampleTodoList(true));

        String output = retrieveResultFrom(outputStream);

        assertThat(output).isEqualTo(
                "| ID | Name            | Description                    | Tags       | Begin  | End    |" + System.lineSeparator() +
                "========================================================================================" + System.lineSeparator() +
                projectsMenuOutput +
                "projects:> "
        );
    }

    @Test
    void wrongInput() {
        InputStream inputStream = createInputStreamForInput(
                "might be a wrong input" + System.lineSeparator() + "back" + System.lineSeparator());
        OutputStream outputStream = new ByteArrayOutputStream();

        SomeDayMaybeUi ui = new SomeDayMaybeUi(new ConsoleController(outputStream, inputStream));
        ui.menu(getExampleTodoList(true));

        String output = retrieveResultFrom(outputStream);
        assertThat(output).isEqualTo(
                someDayMaybeMenuOutput +
                listHeader +
                "|  0 |     TestProject |             Bla |" + System.lineSeparator() +
                "|  1 |             God |             2nd |" + System.lineSeparator() +
                "someDayMaybe:> " +
                listHeader +
                "|  0 |     TestProject |             Bla |" + System.lineSeparator() +
                "|  1 |             God |             2nd |" + System.lineSeparator() +
                "someDayMaybe:> "
        );
    }
}
