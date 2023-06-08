package hwr.oop.group4.todo.ui;

import hwr.oop.group4.todo.ui.controller.ConsoleController;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class ConsoleUserInterfaceTest {

    private final String loadMenuOutput =
            "Do you want to load from a file? (Otherwise create an empty todo list)" + System.lineSeparator() +
                    "Answer y/Y/yes or n/N/no (leave empty for: no)." + System.lineSeparator() +
                    "main/load:> ";
    private final String mainMenuOutput =
            "[1m<==== Main Menu ====>[0m" + System.lineSeparator() +
                    "Welcome to ToDo!" + System.lineSeparator() +
                    System.lineSeparator() +
                    "Commands: " + System.lineSeparator() +
                    "  intray" + System.lineSeparator() +
                    "  tasks" + System.lineSeparator() +
                    "  projects" + System.lineSeparator() +
                    "  calendar" + System.lineSeparator() +
                    "  someday" + System.lineSeparator() +
                    "  load" + System.lineSeparator() +
                    "  save" + System.lineSeparator() +
                    "  help" + System.lineSeparator() +
                    "    Print this information." + System.lineSeparator() +
                    "  quit" + System.lineSeparator() +
                    "    Quit the program." + System.lineSeparator() +
                    "main:> ";

    private String retrieveResultFrom(OutputStream outputStream) {
        return outputStream.toString();
    }

    private InputStream createInputStreamForInput(String input) {
        byte[] inputInBytes = input.getBytes();
        return new ByteArrayInputStream(inputInBytes);
    }

    @Test
    void canCreateNewTodoList() {
        InputStream inputStream = createInputStreamForInput(System.lineSeparator() + "load" + System.lineSeparator() +
                "n" + System.lineSeparator() + "quit" + System.lineSeparator());
        OutputStream outputStream = new ByteArrayOutputStream();

        ConsoleUserInterface ui = new ConsoleUserInterface(new ConsoleController(outputStream, inputStream));
        ui.mainMenu();

        String output = retrieveResultFrom(outputStream);
        assertThat(output).isEqualTo(
                loadMenuOutput +
                        mainMenuOutput +
                        loadMenuOutput +
                        "main:> "
        );
    }

    @Test
    void canLoadAndSaveTodoListFromFile() {
        InputStream inputStream = createInputStreamForInput("yes" + System.lineSeparator() + "save" + System.lineSeparator() +
                "load" + System.lineSeparator() + "Y" + System.lineSeparator() + "quit" + System.lineSeparator()
        );
        OutputStream outputStream = new ByteArrayOutputStream();

        ConsoleUserInterface ui = new ConsoleUserInterface(new ConsoleController(outputStream, inputStream));
        ui.mainMenu();

        String output = retrieveResultFrom(outputStream);

        assertThat(output).isEqualTo(
                loadMenuOutput +
                        mainMenuOutput +
                        "main:> " +
                        loadMenuOutput +
                        "main:> "
        );
    }

    @Test
    void canOpenProjectsMenu() {
        InputStream inputStream = createInputStreamForInput(System.lineSeparator() + "protsch" + System.lineSeparator() +
                "projects" + System.lineSeparator() + "back" + System.lineSeparator() + "quit" + System.lineSeparator());
        OutputStream outputStream = new ByteArrayOutputStream();

        ConsoleUserInterface ui = new ConsoleUserInterface(new ConsoleController(outputStream, inputStream));
        ui.mainMenu();

        String output = retrieveResultFrom(outputStream);

        assertThat(output).isEqualTo(
                loadMenuOutput +
                        mainMenuOutput +
                        "main:> " +
                        "| ID | Name            | Description                    | Tags       | Begin  | End    |" + System.lineSeparator() +
                        "========================================================================================" + System.lineSeparator() +
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
                        "  help" + System.lineSeparator() +
                        "    Print this information." + System.lineSeparator() +
                        "  back" + System.lineSeparator() +
                        "    Returns to the previous menu." + System.lineSeparator() +
                        "projects:> " +
                        "main:> "
        );
    }

    @Test
    void canOpenIntrayMenu() {
        InputStream inputStream = createInputStreamForInput(System.lineSeparator() +
                "intray" + System.lineSeparator() +
                "back" + System.lineSeparator() +
                "quit" + System.lineSeparator()
        );
        OutputStream outputStream = new ByteArrayOutputStream();

        ConsoleUserInterface ui = new ConsoleUserInterface(new ConsoleController(outputStream, inputStream));
        ui.mainMenu();

        String output = retrieveResultFrom(outputStream);

        assertThat(output).isEqualTo(
                loadMenuOutput +
                        mainMenuOutput +
                        "| ID | Name                 | Description                                        |" + System.lineSeparator() +
                        "==================================================================================" + System.lineSeparator() +
                        "[1m<==== Intray Menu ====>[0m" + System.lineSeparator() +
                        "Manage your fleeting thoughts!" + System.lineSeparator() +
                        System.lineSeparator() +
                        "Commands: " + System.lineSeparator() +
                        "  list" + System.lineSeparator() +
                        "    List all ideas." + System.lineSeparator() +
                        "  new" + System.lineSeparator() +
                        "    Create a new idea." + System.lineSeparator() +
                        "  remove" + System.lineSeparator() +
                        "    Remove an idea" + System.lineSeparator() +
                        "    -id <id>" + System.lineSeparator() +
                        "      ID of the idea to be removed." + System.lineSeparator() +
                        "  task" + System.lineSeparator() +
                        "    Create a task from an idea" + System.lineSeparator() +
                        "    -id <id>" + System.lineSeparator() +
                        "      ID of the idea to be used." + System.lineSeparator() +
                        "  help" + System.lineSeparator() +
                        "    Print this information." + System.lineSeparator() +
                        "  back" + System.lineSeparator() +
                        "    Return to the previous menu." + System.lineSeparator() +
                        "intray:> " +
                        "main:> "
        );
    }

    @Test
    void canOpenTasksMenu() {
        InputStream inputStream = createInputStreamForInput(System.lineSeparator() +
                "tasks" + System.lineSeparator() +
                "back" + System.lineSeparator() +
                "quit" + System.lineSeparator());
        OutputStream outputStream = new ByteArrayOutputStream();

        ConsoleUserInterface ui = new ConsoleUserInterface(new ConsoleController(outputStream, inputStream));
        ui.mainMenu();

        final String output = retrieveResultFrom(outputStream);

        assertThat(output).
                isEqualTo(loadMenuOutput +
                        mainMenuOutput +
                        "[1m<==== Task Menu ====>[0m" + System.lineSeparator() +
                        "List and Edit your Tasks." + System.lineSeparator() + System.lineSeparator() +
                        "Commands: " + System.lineSeparator() +
                        "  list" + System.lineSeparator() +
                        "    List all tasks." + System.lineSeparator() +
                        "  new" + System.lineSeparator() +
                        "    Create a new task." + System.lineSeparator() +
                        "  edit" + System.lineSeparator() +
                        "    Edit the attributes of a project." + System.lineSeparator() +
                        "    -id <id>" + System.lineSeparator() +
                        "      ID of the task to be edited." + System.lineSeparator() +
                        "    -name <name>" + System.lineSeparator() +
                        "      Change the name of the task." + System.lineSeparator() +
                        "    -desc <desc>" + System.lineSeparator() +
                        "      Change the description of the task." + System.lineSeparator() +
                        "    -priority <priority>" + System.lineSeparator() +
                        "      Change the priority of the task." + System.lineSeparator() +
                        "    -deadline <deadline>" + System.lineSeparator() +
                        "      Change the deadline of the task." + System.lineSeparator() +
                        "    -addTags <tag> <tag>" + System.lineSeparator() +
                        "      Add a new tags." + System.lineSeparator() +
                        "    -removeTag <tag> <tag>" + System.lineSeparator() +
                        "      Remove tags." + System.lineSeparator() +
                        "  remove" + System.lineSeparator() +
                        "    Remove a task." + System.lineSeparator() +
                        "    -id <id>" + System.lineSeparator() +
                        "      ID of the task to be removed" + System.lineSeparator() +
                        "  removeAllDone" + System.lineSeparator() +
                        "    Remove all task that are done." + System.lineSeparator() +
                        "  complete" + System.lineSeparator() +
                        "    Mark a task as done." + System.lineSeparator() +
                        "    -id <id>" + System.lineSeparator() +
                        "      ID of the completed task." + System.lineSeparator() +
                        "  progress" + System.lineSeparator() +
                        "    Mark a task as in progress." + System.lineSeparator() +
                        "    -id <id>" + System.lineSeparator() +
                        "      ID of the task which is to be set to in progress." + System.lineSeparator() +
                        "  open" + System.lineSeparator() +
                        "    Reopens a task." + System.lineSeparator() +
                        "    -id <id>" + System.lineSeparator() +
                        "      ID of the task which is to be set to open." + System.lineSeparator() +
                        "  back" + System.lineSeparator() +
                        "    Returns to the previous menu." + System.lineSeparator() +
                        "  help" + System.lineSeparator() +
                        "    Prints this Menu again." + System.lineSeparator() +
                        "| ID | Name            | Description                    | Tags       | Deadline | Priority | Status     |" + System.lineSeparator() +
                        "=========================================================================================================" + System.lineSeparator() +
                        "tasks:> " +
                        "main:> "
                );
    }

    @Test
    void canOpenCalendarMenu() {
        InputStream inputStream = createInputStreamForInput(System.lineSeparator() +
                "calendar" + System.lineSeparator() +
                "back" + System.lineSeparator() +
                "quit" + System.lineSeparator());
        OutputStream outputStream = new ByteArrayOutputStream();

        ConsoleUserInterface ui = new ConsoleUserInterface(new ConsoleController(outputStream, inputStream));
        ui.mainMenu();

        String output = retrieveResultFrom(outputStream);
        assertThat(output).contains(loadMenuOutput +
                mainMenuOutput +
                "[1m<==== Calendar Menu ====>[0m" + System.lineSeparator() +
                System.lineSeparator() +
                "Commands: " + System.lineSeparator() +
                "  today" + System.lineSeparator() +
                "  nextWeek" + System.lineSeparator() +
                "  lastWeek" + System.lineSeparator() +
                "  back" + System.lineSeparator() +
                "    Return to the previous menu" + System.lineSeparator() +
                System.lineSeparator()
        );
    }

    @Test
    void canOpenSomeDayMaybe() {
        InputStream inputStream = createInputStreamForInput(System.lineSeparator() +
                "someday" + System.lineSeparator() +
                "back" + System.lineSeparator() +
                "quit" + System.lineSeparator()
        );
        OutputStream outputStream = new ByteArrayOutputStream();

        ConsoleUserInterface ui = new ConsoleUserInterface(new ConsoleController(outputStream, inputStream));
        ui.mainMenu();

        String output = retrieveResultFrom(outputStream);

        assertThat(output).isEqualTo(
                loadMenuOutput +
                        mainMenuOutput +
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
                        "| ID | Name            | Description     |" + System.lineSeparator() +
                        "==========================================" + System.lineSeparator() +
                        "someday:> " +
                        "main:> "
        );
    }
}
