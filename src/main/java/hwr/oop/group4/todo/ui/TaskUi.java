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

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
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
        final List<String> modifiable = new ArrayList<>(prefixes);
        modifiable.add("task");
        menu(project.getTasks(), Collections.unmodifiableList(modifiable));
    }

    private void menu(Set<Task> tasks, List<String> prefixes) {
        this.tasks = tasks;
        this.prefixes = prefixes;

        consoleController.output(getMenu());
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
                    new Command("back", args -> shouldReturn.set(true))
            ), new Command("wrongInput", args -> {
            }));
        }
    }

    private String getMenu() {
        return new Menu("Task Menu", "List and Edit your Tasks.", List.of(
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
                        new EntryArgument("id <id>", "ID of the task which is to be set to in progress.")
                )),
                new Entry("open", "Reopens a task.", List.of(
                        new EntryArgument("id <id>", "ID of the task which is to be set to open.")
                )),
                new Entry("back", "Returns to the previous menu."),
                new Entry("help", "Prints this Menu again.")
        )).toString();
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
        final Task.TaskBuilder builder = new Task.TaskBuilder();

        final Optional<String> name = consoleHelper.getStringParameter(args, "name");
        final Optional<String> desc = consoleHelper.getStringParameter(args, "desc");
        final Optional<String> deadline = consoleHelper.getStringParameter(args, "deadline");
        final Optional<String> priority = consoleHelper.getStringParameter(args, "priority");
        final Optional<String> addTags = consoleHelper.getStringParameter(args, "addTags");
        final Optional<String> removeTags = consoleHelper.getStringParameter(args, "removeTags");

        builder.name(name.orElse(task.getName()));
        builder.description(desc.orElse(task.getDescription()));
        builder.deadline(consoleHelper.parseDate(deadline.orElse("")).orElse(task.getDeadline()));

        try {
            builder.priority(Integer.parseInt(priority.orElse("")));
        } catch (NumberFormatException e) {
            builder.priority(task.getPriority());
        }

        final Set<Tag> taskTags = task.getTags();
        tasks.remove(task);

        addTags.ifPresent(tags -> Arrays.stream(tags.split(" "))
                .forEach(tag -> taskTags.add(new Tag(tag)))
        );

        removeTags.ifPresent(tags -> Arrays.stream(tags.split(" "))
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
        final List<String> removePrefix = new ArrayList<>(prefixes);
        removePrefix.add("remove");
        removePrefix.add(consoleHelper.getStringParameter(args, "id").orElse("?"));

        final Task task = tasks.stream().toList().get(consoleHelper.getId(args, tasks.size()));
        final String confirmation = "Do you really want to remove " + task.getName() + "?";
        if (consoleController.inputBool(removePrefix, confirmation, false)) {
            tasks.remove(task);
        }
    }
}