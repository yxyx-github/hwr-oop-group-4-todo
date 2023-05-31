package hwr.oop.group4.todo.ui;

import hwr.oop.group4.todo.commons.exceptions.TodoUiRuntimeException;
import hwr.oop.group4.todo.core.TodoList;
import hwr.oop.group4.todo.core.api.PersistenceFileUseCase;
import hwr.oop.group4.todo.core.api.TodoListCreationUseCase;
import hwr.oop.group4.todo.ui.controller.ConsoleController;
import hwr.oop.group4.todo.ui.controller.ConsoleHelper;
import hwr.oop.group4.todo.ui.controller.command.Command;
import hwr.oop.group4.todo.ui.controller.command.CommandArgument;
import hwr.oop.group4.todo.ui.controller.menu.Entry;
import hwr.oop.group4.todo.ui.controller.menu.EntryArgument;
import hwr.oop.group4.todo.ui.controller.menu.Menu;

import java.io.File;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;

public class ConsoleUserInterface {

    final PersistenceFileUseCase persistenceAdapter;
    final TodoListCreationUseCase creationAdapter;

    private final ConsoleController consoleController;
    private final ProjectUi projectUi;
    private final IntrayUi intrayUi;
    private final TaskUi taskUi;
    private TodoList todoList;

    public ConsoleUserInterface(ConsoleController consoleController, PersistenceFileUseCase persistenceAdapter,
                                TodoListCreationUseCase creationAdapter) {
        projectUi = new ProjectUi(consoleController);
        intrayUi = new IntrayUi(consoleController);
        taskUi = new TaskUi(consoleController);
        this.persistenceAdapter = persistenceAdapter;
        this.creationAdapter = creationAdapter;
        initialiseTodoList();
    }

    public void mainMenu() {
        Menu menu = new Menu("Main Menu", "Welcome to ToDo!", List.of(
                new Entry("intray",   ""),
                new Entry("tasks",    ""),
                new Entry("projects", ""),
                new Entry("calendar", ""),
                new Entry("new", ""),
                new Entry("load",     "",
                        List.of(new EntryArgument("file", "A path to the file."))),
                new Entry("save",     "",
                        List.of(new EntryArgument("file", "A path to the file."))),
                new Entry("quit",     "Quit the program.")
        ));

        AtomicBoolean shouldReturn = new AtomicBoolean(false);
        while (!shouldReturn.get()) {
            consoleController.output(menu.toString());

            consoleController.inputOptions(List.of("main"), List.of(
                    new Command("intray",   args -> intrayUi.menu(todoList)),
                    new Command("tasks",    args -> taskUi.menu(todoList)),
                    new Command("projects", args -> projectUi.menu(todoList)),
                    new Command("calendar", args -> {}),
                    new Command("new", this::create),
                    new Command("load", this::load),
                    new Command("save", this::save),
                    new Command("quit", args -> shouldReturn.set(true))
            ), new Command("wrongInput", args -> {}));
        }
    }


    private void create(Collection<CommandArgument> args) {
        this.todoList = creationAdapter.create();
    }

    private void save(Collection<CommandArgument> args) {
        final Optional<String> filePath = consoleHelper.getStringParameter(args, "file");
        if (filePath.isEmpty()) {
            consoleController.outputLine("A filepath is needed inorder to save.");
            return;
        }
        try {
            persistenceAdapter.save(todoList, new File(filePath.get()));
        } catch (TodoUiRuntimeException e) {
            consoleController.outputLine("There was an error while saving.");
            e.printStackTrace();
        }
    }

    private void load(Collection<CommandArgument> args) {
        final Optional<String> filePath = consoleHelper.getStringParameter(args, "file");
        if (filePath.isEmpty()) {
            consoleController.outputLine("A filepath is needed inorder to load a file.");
            return;
        }
        try {
            todoList = persistenceAdapter.load(new File(filePath.get()));
        } catch (TodoUiRuntimeException e) {
            consoleController.outputLine("There was an error while loading");
            e.printStackTrace();
        }
    }

    private void initialiseTodoList() {
        final String question = "Do you want to load from a file? (Otherwise create an empty todo list)";
        final boolean loadFromFile = consoleController.inputBool(List.of("main", "init"), question, false);

        if (!loadFromFile) {
            todoList =  creationAdapter.create();
            return;
        }

        boolean success = false;
        while (!success) {
            final Optional<String> path = consoleController.input(List.of("main", "init", "load", "path"), "Enter the path to the file which is supposed to be loaded.");
            if (path.isEmpty()) {
                continue;
            }
            try {
                todoList = persistenceAdapter.load(new File(path.get()));
                success = true;
            } catch (TodoUiRuntimeException e) {
                consoleController.outputLine("There was an error while loading");
                e.printStackTrace();
            }
        }
    }

}
