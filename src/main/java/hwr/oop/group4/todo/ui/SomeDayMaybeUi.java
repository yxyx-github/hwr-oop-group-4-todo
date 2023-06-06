package hwr.oop.group4.todo.ui;

import hwr.oop.group4.todo.core.TodoList;
import hwr.oop.group4.todo.ui.controller.ConsoleController;
import hwr.oop.group4.todo.ui.controller.ConsoleHelper;
import hwr.oop.group4.todo.core.Project;
import hwr.oop.group4.todo.ui.controller.command.Command;
import hwr.oop.group4.todo.ui.controller.command.CommandArgument;
import hwr.oop.group4.todo.ui.controller.menu.Entry;
import hwr.oop.group4.todo.ui.controller.menu.EntryArgument;
import hwr.oop.group4.todo.ui.controller.menu.Menu;
import hwr.oop.group4.todo.ui.controller.tables.ColumnConfig;
import hwr.oop.group4.todo.ui.controller.tables.Table;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public class SomeDayMaybeUi {

    private final ConsoleController consoleController;
    private final ConsoleHelper consoleHelper;
    private TodoList todoList;

    public SomeDayMaybeUi(ConsoleController consoleController) {
        this.consoleController = consoleController;
        this.consoleHelper = new ConsoleHelper();
    }

    public void menu(TodoList todoList) {
        this.todoList = todoList;

        Menu menu = new Menu("SomeDayMaybe List", "Manage your projects which you might take care of!",
                List.of(
                new Entry("list", ""),
                new Entry("new", "add something to the list"),
                new Entry("remove", "remove it from the list", List.of(
                        new EntryArgument("id <id>", "ID of the project to be removed.")
                )),
                new Entry("move to projects", "push your work to projects."),
                new Entry("back", "Returns to the previous menu.")
        ));
        consoleController.output(menu.toString());

        AtomicBoolean shouldReturn = new AtomicBoolean(false);
        while (!shouldReturn.get()) {
            listSomeDayMaybe(null);
            final int size = todoList.getMaybeList().size();
            consoleController.inputOptions(List.of("someday"), List.of(
                    new Command("list", this::listSomeDayMaybe),
                    new Command("new", this::newSomeDayMaybe),
                    new Command("remove", args -> consoleController.callWithValidId(true, size, args, this::removeSomeDayMaybe)),
                    new Command("move to projects", args -> {}),
                    new Command("back",   args -> shouldReturn.set(true))
            ), new Command("wrongInput", args -> {}));
        }
    }

    private void listSomeDayMaybe(Collection<CommandArgument> args) {
        final List<Project> someDay = todoList.getMaybeList();
        final int idColumnLength = Math.max((int) Math.ceil(Math.log10(someDay.size()) - 2), 2);
        final Table someDayTable = new Table(List.of(
           new ColumnConfig("ID", idColumnLength),
           new ColumnConfig("Name", 15),
           new ColumnConfig("Description", 15)
        ));

        for (int i = 0; i < someDay.size(); i++) {
            final Project someDayList = someDay.get(i);
            someDayTable.addRow(
                    String.valueOf(i),
                    someDayList.getName(),
                    someDayList.getDescription());
        }
        consoleController.output(someDayTable.toString());
    }

    private void newSomeDayMaybe(Collection<CommandArgument> args) {
        String name = consoleController.input(List.of("project", "new", "name")).orElseThrow();
        String desc = consoleController.input(List.of("project", "new", "description")).orElseThrow();

        Project someDayMaybe = new Project.ProjectBuilder()
                .name(name)
                .description(desc)
                .begin(LocalDateTime.now())
                .end(LocalDateTime.now())
                .build();
        todoList.addSomedayMaybeProject(someDayMaybe);
    }

    private void removeSomeDayMaybe(Collection<CommandArgument> args) {
        final int id = consoleHelper.getId(args, todoList.getMaybeList().size());

        final String someDayMaybeName = todoList.getMaybeList().get(id).getName();
        final String confirmation = "Do you really want to remove " + someDayMaybeName + "?";
        if (consoleController.inputBool(List.of("projects", "remove"), confirmation, false)) {
            todoList.removeSomedayMaybeProject(todoList.getMaybeList().get(id));
        }
    }
    private void moveToProjects(Collection<CommandArgument> args) {

    }
}
