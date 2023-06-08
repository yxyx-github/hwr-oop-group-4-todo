package hwr.oop.group4.todo.ui;

import hwr.oop.group4.todo.core.api.adapter.PersistenceAdapter;
import hwr.oop.group4.todo.core.api.adapter.TodoListCreationAdapter;
import hwr.oop.group4.todo.persistence.FileAdapter;
import hwr.oop.group4.todo.ui.controller.ConsoleController;

public class TodoUiApplication {

    public static void main(String[] args) {
        final FileAdapter fileAdapter = new FileAdapter();
        ConsoleUserInterface ui = new ConsoleUserInterface(new ConsoleController(System.out, System.in),
                new PersistenceAdapter(fileAdapter, fileAdapter), new TodoListCreationAdapter());
        ui.mainMenu();
    }
}
