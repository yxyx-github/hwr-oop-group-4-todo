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
        showHelp(null);

        AtomicBoolean shouldReturn = new AtomicBoolean(false);
        while (!shouldReturn.get()) {
            listSomeDayMaybe(null);
            final int size = todoList.getMaybeList().size();
            consoleController.inputOptions(List.of("someday"), List.of(
                    new Command("list", this::listSomeDayMaybe),
                    new Command("new", this::newSomeDayMaybe),
                    new Command("remove", args -> consoleController.callWithValidId(true, size, args, this::removeSomeDayMaybe)),
                    new Command("move", args -> consoleController.callWithValidId(true, size, args, this::moveToProjects)),
                    new Command("help", this::showHelp),
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
        String name = consoleController.input(List.of("someday", "new", "name")).orElseThrow();
        String desc = consoleController.input(List.of("someday", "new", "description")).orElseThrow();

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
        if (consoleController.inputBool(List.of("someday", "remove"), confirmation, false)) {
            todoList.removeSomedayMaybeProject(todoList.getMaybeList().get(id));
        }
    }
    private void moveToProjects(Collection<CommandArgument> args) {
        final int id = consoleHelper.getId(args, todoList.getMaybeList().stream().toList().size());
        final Project someDayMaybe = todoList.getMaybeList().stream().toList().get(id);
        LocalDateTime begin = consoleController.inputDate(List.of("someday", "move", "begin"));
        LocalDateTime end = consoleController.inputDate(List.of("someday", "move", "end"));

        Project someProject = new Project.ProjectBuilder()
                .name(todoList.getMaybeList().get(id).getName())
                .description(todoList.getMaybeList().get(id).getDescription())
                .begin(begin)
                .end(end)
                .build();
        todoList.addProject(someProject);
        todoList.removeSomedayMaybeProject(someDayMaybe);
    }

    private void showHelp(Collection<CommandArgument> args) {
        Menu menu = new Menu("SomeDayMaybe List",
                "Manage your list of tasks you want to take care of!", List.of(
                new Entry("list",   ""),
                new Entry("new",    "Add something to the list."),
                new Entry("remove", "Remove it from the list.", List.of(
                        new EntryArgument("id <id>", "ID of the list to be removed.")
                )),
                new Entry("move",   "Move it to projects.", List.of(
                        new EntryArgument("id <id>", "ID of the list to be used.")
                )),
                new Entry("help",   "Print this information."),
                new Entry("back",   "Returns to the previous menu.")
        ));
        consoleController.output(menu.toString());
    }

}
