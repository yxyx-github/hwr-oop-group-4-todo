package hwr.oop.group4.todo.ui;

import hwr.oop.group4.todo.core.*;
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
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;

public class TaskUi {
    private final ConsoleController consoleController;
    private final ConsoleHelper consoleHelper;
    private final TaskCreationUi taskCreationUi;
    private List<String> prefixes;
    private Set<Task> tasks;

    public TaskUi(ConsoleController consoleController) {
        this.consoleController = consoleController;
        this.consoleHelper = new ConsoleHelper();
        this.taskCreationUi = new TaskCreationUi(consoleController);
    }

    public void menu(TodoList todoList) {
        menu(todoList.getLoseTasks(), List.of("tasks"));
    }

    public void menu(Project project, List<String> prefixes) {
        menu(project.getTasks(), consoleHelper.addPrefix(prefixes, "tasks"));
    }

    private void menu(Set<Task> tasks, List<String> prefixes) {
        this.tasks = tasks;
        this.prefixes = prefixes;

        showHelp(null);
        list(null);

        final AtomicBoolean shouldReturn = new AtomicBoolean(false);
        while (!shouldReturn.get()) {
            final int size = tasks.size();
            consoleController.inputOptions(prefixes, List.of(
                    new Command("list", this::list),
                    new Command("new", this::create),
                    new Command("edit", args -> consoleController.callWithValidId(true, size, args, this::edit)),
                    new Command("remove", args -> consoleController.callWithValidId(true, size, args, this::remove)),
                    new Command("removeAllDone", this::removeAllDone),
                    new Command("complete", args -> consoleController.callWithValidId(true, size, args, this::complete)),
                    new Command("inProgress", args -> consoleController.callWithValidId(true, size, args, this::progress)),
                    new Command("open", args -> consoleController.callWithValidId(true, size, args, this::open)),
                    new Command("help", this::showHelp),
                    new Command("back", args -> shouldReturn.set(true))
            ), new Command("wrongInput", args -> {
            }));
        }
    }

    private void showHelp(Collection<CommandArgument> args) {
        Menu menu = new Menu("Task Menu", "List and Edit your Tasks.", List.of(
                new Entry("list", "List all tasks."),
                new Entry("new",  "Create a new task."),
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
                        new EntryArgument("id <id>", "ID of the task which is to be set to in progress.")
                )),
                new Entry("open", "Reopens a task.", List.of(
                        new EntryArgument("id <id>", "ID of the task which is to be set to open.")
                )),
                new Entry("help", "Print this information."),
                new Entry("back", "Returns to the previous menu.")
        ));
        consoleController.output(menu.toString());
    }


    private void list(Collection<CommandArgument> args) {
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

        final List<Task> taskList = tasks.stream().toList();
        for (int i = 0; i < taskList.size(); i++) {
            final Task task = taskList.get(i);
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

    private void create(Collection<CommandArgument> args) {
        tasks.add(taskCreationUi.create(null, prefixes));
    }

    private void edit(Collection<CommandArgument> args) {
        final Task task = tasks.stream().toList().get(consoleHelper.getId(args, tasks.size()));
        tasks.remove(task);
        final Task.TaskBuilder builder = new Task.TaskBuilder();

        builder.name(consoleHelper.getStringParameter(args, "name").orElse(task.getName()));
        builder.description(consoleHelper.getStringParameter(args, "desc").orElse(task.getDescription()));
        builder.deadline(consoleHelper.parseDate(consoleHelper.getStringParameter(args, "deadline").orElse(""))
                .orElse(task.getDeadline()));

        try {
            builder.priority(Integer.parseInt(consoleHelper.getStringParameter(args, "priority").orElse("")));
        } catch (NumberFormatException e) {
            builder.priority(task.getPriority());
        }

        final Set<Tag> taskTags = task.getTags();
        consoleHelper.getStringParameter(args, "addTags")
                .ifPresent(tags -> Arrays.stream(tags.split(" "))
                .forEach(tag -> taskTags.add(new Tag(tag)))
        );

        consoleHelper.getStringParameter(args, "removeTags")
                .ifPresent(tags -> Arrays.stream(tags.split(" "))
                .forEach(tag -> taskTags.remove(new Tag(tag)))
        );

        builder.addTags(taskTags.toArray(Tag[]::new));
        tasks.add(builder.build());
    }

    private void open(Collection<CommandArgument> args) {
        tasks.stream().toList().get(consoleHelper.getId(args, tasks.size())).open();
    }

    private void progress(Collection<CommandArgument> args) {
        tasks.stream().toList().get(consoleHelper.getId(args, tasks.size())).inProgress();
    }

    private void complete(Collection<CommandArgument> args) {
        tasks.stream().toList().get(consoleHelper.getId(args, tasks.size())).closed();
    }

    private void removeAllDone(Collection<CommandArgument> args) {
        tasks.removeIf(task -> task.getStatus() == Status.CLOSED);
    }

    private void remove(Collection<CommandArgument> args) {
        final List<String> basePrefix = consoleHelper.addPrefix(prefixes, "remove",
                        consoleHelper.getStringParameter(args, "id").orElse("?"));

        final Task task = tasks.stream().toList().get(consoleHelper.getId(args, tasks.size()));
        final String confirmation = "Do you really want to remove " + task.getName() + "?";
        if (consoleController.inputBool(basePrefix, confirmation, false)) {
            tasks.remove(task);
        }
    }
}
