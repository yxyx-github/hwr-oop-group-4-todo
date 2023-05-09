package hwr.oop.group4.todo.ui;

import hwr.oop.group4.todo.core.Idea;
import hwr.oop.group4.todo.core.Project;
import hwr.oop.group4.todo.core.Task;
import hwr.oop.group4.todo.core.TodoList;
import hwr.oop.group4.todo.ui.controller.ConsoleController;
import hwr.oop.group4.todo.ui.controller.ConsoleHelper;
import hwr.oop.group4.todo.ui.controller.command.Command;
import hwr.oop.group4.todo.ui.controller.command.CommandArgument;
import hwr.oop.group4.todo.ui.controller.menu.Entry;
import hwr.oop.group4.todo.ui.controller.menu.EntryArgument;
import hwr.oop.group4.todo.ui.controller.menu.Menu;
import hwr.oop.group4.todo.ui.controller.tables.ColumnConfig;
import hwr.oop.group4.todo.ui.controller.tables.Table;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public class TaskUi {
    private final ConsoleController consoleController;
    private final ConsoleHelper consoleHelper;

    private List<Task> tasks;

    public TaskUi(ConsoleController consoleController) {
        this.consoleController = consoleController;
        this.consoleHelper = new ConsoleHelper();
    }

    public void menu(TodoList todoList) {
        menu(todoList.getLoseTasks().stream().toList(), List.of("tasks"));
    }

    public void menu(Project project, List<String> prefixes) {
        final List<String> modifiable = new ArrayList<>(prefixes);
        modifiable.add("task");
        menu(project.getTasks().stream().toList(), Collections.unmodifiableList(modifiable));
    }

    private void menu(List<Task> tasks, List<String> prefixes) {
        this.tasks = tasks;

        final String menu =  new Menu("Task Menu", "List and Edit your Tasks." , List.of(
                new Entry("list", "List all tasks."),
                new Entry("new", "Create a new task."),
                new Entry("edit", "Edit the attributes of a project.", List.of(
                        new EntryArgument("id <id>", "ID of the task to be edited."),
                        new EntryArgument("name <name>", "Change the name of the task."),
                        new EntryArgument("desc <desc>", "Change the description of the task."),
                        new EntryArgument("priority <priority>", "Change the priority of the task."),
                        new EntryArgument("deadline <deadline>", "Change the deadline of the task."),
                        new EntryArgument("addTags <tag> <tag>", "Add a new tags."),
                        new EntryArgument("removeTag <tag> <tag>", "Remove tags.")
                )),
                new Entry("remove", "Remove a task.", List.of(
                        new EntryArgument("id <id>", "ID of the task to be removed")
                )),
                new Entry("removeAllDone", "Remove all task that are done."),
                new Entry("complete", "Mark a task as done.", List.of(
                        new EntryArgument("id <id>", "ID of the completed task.")
                )),
                new Entry("progress", "Mark a task as in progress.", List.of(
                        new EntryArgument("id <id>", "ID of the task which is to be set to in progrss.")
                )),
                new Entry("back", "Returns to the previous menu."),
                new Entry("help", "Prints this Menu again.")
        )).toString();

        consoleController.output(menu);
        listTasks(null);
        final AtomicBoolean shouldReturn = new AtomicBoolean(false);
        while (!shouldReturn.get()) {
            consoleController.inputOptions(List.of("loseTasks"), List.of(
                    new Command("list",   this::listTasks),
                    new Command("new",    this::createTask),
                    new Command("edit",   this::editTask),
                    new Command("remove", this::removeTask),
                    new Command("removeAllDone", this::removeAllDone),
                    new Command("removeAllDone", this::completeTask),
                    new Command("removeAllDone", this::progressTask),
                    new Command("back",   args -> shouldReturn.set(true))
            ), new Command("wrongInput", args -> {}));
        }
    }


    private void listTasks(Collection<CommandArgument<String>> args) {
        final int idColumnLength = Math.max((int) Math.ceil(Math.log10(tasks.size()) - 2), 2);
        final Table projectTable = new Table(List.of(
                new ColumnConfig("ID", idColumnLength),
                new ColumnConfig("Name", 15),
                new ColumnConfig("Description", 30),
                new ColumnConfig("Tags", 10),
                new ColumnConfig("Deadline", 8),
                new ColumnConfig("Priority", 8),
                new ColumnConfig("Status", 10)
        ));

        for (int i = 0; i < tasks.size(); i++) {
            final Task task = tasks.get(i);
            projectTable.addRow(
                    String.valueOf(i),
                    task.getName(),
                    task.getDescription(),
                    consoleHelper.concatTagsToString(task.getTags()),
                    task.getDeadline() == null ? "" : task.getDeadline().format(DateTimeFormatter.ofPattern("dd.MM.yy")),
                    String.valueOf(task.getPriority()),
                    task.getStatus().name()
            );
        }

        consoleController.output(projectTable.toString());
    }

    private void createTask(Collection<CommandArgument<String>> args) {

    }

    private void createTask(Idea idea) {
    }

    private void editTask(Collection<CommandArgument<String>> args) {
    }

    private void progressTask(Collection<CommandArgument<String>> args) {
    }

    private void completeTask(Collection<CommandArgument<String>> args) {
    }

    private void removeAllDone(Collection<CommandArgument<String>> args) {
    }

    private void removeTask(Collection<CommandArgument<String>> args) {
    }



}
