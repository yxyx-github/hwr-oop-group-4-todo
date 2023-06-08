package hwr.oop.group4.todo.ui;

import hwr.oop.group4.todo.core.Idea;
import hwr.oop.group4.todo.core.Task;
import hwr.oop.group4.todo.ui.controller.ConsoleController;
import hwr.oop.group4.todo.ui.controller.ConsoleHelper;

import java.time.LocalDateTime;
import java.util.List;

public class TaskCreationUi {

    private final ConsoleController consoleController;
    private final ConsoleHelper helper;

    public TaskCreationUi(ConsoleController consoleController) {
        this.consoleController = consoleController;
        this.helper = new ConsoleHelper();
    }

    public Task create(Idea idea, List<String> prefixes) {
        final List<String> basePrefix = helper.addPrefix(prefixes, "new");
        final Task.TaskBuilder builder = new Task.TaskBuilder();
        if (idea == null) {
            final String name = consoleController.input(helper.addPrefix(basePrefix, "name")).orElseThrow();
            final String desc = consoleController.input(helper.addPrefix(basePrefix, "description")).orElseThrow();
            builder.name(name).description(desc);
        } else {
            builder.fromIdea(idea);
        }
        final int priority = consoleController.inputInt(helper.addPrefix(basePrefix, "priority"));
        final LocalDateTime deadline = consoleController.inputDate(helper.addPrefix(basePrefix, "deadline"));

        return builder.priority(priority)
                .deadline(deadline)
                .build();
    }
}
