package hwr.oop.group4.todo.ui;

import hwr.oop.group4.todo.core.Project;
import hwr.oop.group4.todo.core.Tag;
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

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;

public class ProjectUi {

    private final ConsoleController consoleController;
    private final ConsoleHelper consoleHelper;
    private final TaskUi taskUi;
    private TodoList todoList;

    public ProjectUi(ConsoleController consoleController) {
        this.consoleController = consoleController;
        this.consoleHelper = new ConsoleHelper();
        this.taskUi = new TaskUi(consoleController);
    }

    public void menu(TodoList todoList) {
        this.todoList = todoList;
        listProjects(null);
        showHelp(null);

        AtomicBoolean shouldReturn = new AtomicBoolean(false);
        while (!shouldReturn.get()) {
            final int size = todoList.getProjects().size();
            consoleController.inputOptions(List.of("projects"), List.of(
                    new Command("list",   this::listProjects),
                    new Command("new",    this::newProject),
                    new Command("tasks",  args -> consoleController.callWithValidId(true, size, args, this::tasks)),
                    new Command("edit",   args -> consoleController.callWithValidId(true, size, args, this::editProject)),
                    new Command("remove", args -> consoleController.callWithValidId(true, size, args, this::removeProject)),
                    new Command("help",   this::showHelp),
                    new Command("back",   args -> shouldReturn.set(true))
            ), new Command("wrongInput", args -> {}));
        }
    }

    private void listProjects(Collection<CommandArgument> args) {
        final List<Project> projects = todoList.getProjects();
        final int idColumnLength = Math.max((int) Math.ceil(Math.log10(projects.size()) - 2), 2);
        final Table projectTable = new Table(List.of(
                new ColumnConfig("ID", idColumnLength),
                new ColumnConfig("Name", 15),
                new ColumnConfig("Description", 30),
                new ColumnConfig("Tags", 10),
                new ColumnConfig("Begin", 6),
                new ColumnConfig("End", 6)
        ));

        for (int i = 0; i < projects.size(); i++) {
            final Project project = projects.get(i);
            projectTable.addRow(
                    String.valueOf(i),
                    project.getName(),
                    project.getDescription(),
                    consoleHelper.concatTagsToString(project.getTags()),
                    project.getBegin().format(DateTimeFormatter.ofPattern("dd.MM.")),
                    project.getEnd().format(DateTimeFormatter.ofPattern("dd.MM."))
            );
        }

        consoleController.output(projectTable.toString());
    }

    private void tasks(Collection<CommandArgument> args) {
        final List<Project> projects = todoList.getProjects();
        final int id = consoleHelper.getId(args, todoList.getProjects().size());
        taskUi.menu(projects.get(id), List.of("projects", String.valueOf(id)));
    }

    private void newProject(Collection<CommandArgument> args) {
        String name = consoleController.input(List.of("projects", "new", "name")).orElseThrow();
        String desc = consoleController.input(List.of("projects", "new", "description")).orElseThrow();
        LocalDateTime begin = consoleController.inputDate(List.of("projects", "new", "begin"));
        LocalDateTime end = consoleController.inputDate(List.of("projects", "new", "end"));

        Project project = new Project.ProjectBuilder()
                .name(name)
                .description(desc)
                .begin(begin)
                .end(end)
                .build();

        todoList.addProject(project);
    }

    private void removeProject(Collection<CommandArgument> args) {
        final int id = consoleHelper.getId(args, todoList.getProjects().size());

        final String projectName = todoList.getProjects().get(id).getName();
        final String confirmation = "Do you really want to remove " + projectName + "?";
        if (consoleController.inputBool(List.of("projects", "remove"), confirmation, false)) {
            todoList.removeProject(todoList.getProjects().get(id));
        }
    }

    private void editProject(Collection<CommandArgument> args) {
        final int id = consoleHelper.getId(args, todoList.getProjects().size());

        final Project project = todoList.getProjects().get(id);
        todoList.removeProject(project);
        final Project.ProjectBuilder newProject = new Project.ProjectBuilder();

        final Optional<String> name = consoleHelper.getStringParameter(args, "name");
        newProject.name(name.orElseGet(project::getName));

        final Optional<String> desc = consoleHelper.getStringParameter(args, "desc");
        newProject.description(desc.orElseGet(project::getDescription));

        final Optional<String> beginParam = consoleHelper.getStringParameter(args, "begin");
        final Optional<LocalDateTime> begin = consoleHelper.parseDate(beginParam.orElse(""));
        newProject.begin(begin.orElseGet(project::getBegin));

        final Optional<String> endParam = consoleHelper.getStringParameter(args, "end");
        final Optional<LocalDateTime> end = consoleHelper.parseDate(endParam.orElse(""));
        newProject.end(end.orElseGet(project::getEnd));

        final Collection<Tag> tags = project.getTags();
        final Optional<String> addTags = consoleHelper.getStringParameter(args, "addTags");
        if (addTags.isPresent()) {
            String[] newTagNames = addTags.get().split(" ");
            for (String tag : newTagNames) {
                tags.add(new Tag(tag));
            }
        }
        final Optional<String> removeTags = consoleHelper.getStringParameter(args, "removeTags");
        if (removeTags.isPresent()) {
            String[] removeTagNames = removeTags.get().split(" ");
            for (String tag : removeTagNames) {
                tags.remove(new Tag(tag));
            }
        }
        newProject.addTag(tags.toArray(new Tag[0]));

        newProject.addTasks(project.getTasks().toArray(new Task[0]));
        todoList.addProject(newProject.build());
    }

    private void showHelp(Collection<CommandArgument> args){
        Menu menu = new Menu("Project Menu", "Manage your Projects!", List.of(
                new Entry("list",   "List all projects."),
                new Entry("new",    "Add a new project."),
                new Entry("tasks",  "Open the task menu for a project.", List.of(
                        new EntryArgument("id <id>", "ID of the project.")
                )),
                new Entry("edit",   "Edit the attributes of a project.", List.of(
                        new EntryArgument("id <id>", "ID of the project to be edited."),
                        new EntryArgument("name <name>", "Change the name of the project."),
                        new EntryArgument("desc <desc>", "Change the description of the project."),
                        new EntryArgument("begin", "Change the beginning of the project."),
                        new EntryArgument("end", "Change the end of the project"),
                        new EntryArgument("addTags <tag> [<tag> ...]", "Add a new tag."),
                        new EntryArgument("removeTags <tag> [<tag> ...]", "Remove a tag.")
                )),
                new Entry("remove", "Remove a project.", List.of(
                        new EntryArgument("id <id>", "ID of the project to be removed.")
                )),
                new Entry("help",   "Print this information."),
                new Entry("back",   "Returns to the previous menu.")
        ));
        consoleController.output(menu.toString());
    }

}
