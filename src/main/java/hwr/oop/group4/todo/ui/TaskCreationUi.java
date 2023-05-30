package hwr.oop.group4.todo.ui;

import hwr.oop.group4.todo.core.Idea;
import hwr.oop.group4.todo.core.Task;
import hwr.oop.group4.todo.ui.controller.ConsoleController;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class TaskCreationUi {

    private final ConsoleController consoleController;

    public TaskCreationUi(ConsoleController consoleController) {
        this.consoleController = consoleController;
    }

    public Task create(Idea idea, List<String> prefixes) {
        final List<String> mutablePrefixes = new ArrayList<>(prefixes);
        mutablePrefixes.add("new");
        final Task.TaskBuilder builder = new Task.TaskBuilder();
        if (idea == null) {
            mutablePrefixes.add("name");
            final String name = consoleController.input(mutablePrefixes).orElseThrow();
            mutablePrefixes.remove(mutablePrefixes.size() - 1);
            mutablePrefixes.add("description");
            final String desc = consoleController.input(mutablePrefixes).orElseThrow();
            mutablePrefixes.remove(mutablePrefixes.size() - 1);
            builder.name(name).description(desc);
        } else {
            builder.fromIdea(idea);
        }
        mutablePrefixes.add("priority");
        final int priority = consoleController.inputInt(mutablePrefixes);
        mutablePrefixes.remove(mutablePrefixes.size() - 1);
        mutablePrefixes.add("deadline");
        final LocalDateTime deadline = consoleController.inputDate(mutablePrefixes);

        return builder.priority(priority)
                .deadline(deadline)
                .build();
    }
}
