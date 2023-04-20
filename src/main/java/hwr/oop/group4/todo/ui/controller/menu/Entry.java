package hwr.oop.group4.todo.ui.controller.menu;

import java.util.ArrayList;
import java.util.Collection;

public class Entry {

    private final String title;
    private final String description;
    private final Collection<EntryArgument> arguments;


    public Entry(String title, String description) {
        this(title, description, new ArrayList<>());
    }
    public Entry(String title, String description, Collection<EntryArgument> arguments) {
        this.title = title;
        this.description = description;
        this.arguments = arguments;
    }

    public String toString(String padding) {
        final StringBuilder entryStringBuilder = new StringBuilder()
                .append(padding).append(title).append(System.lineSeparator());
        if (!description.isBlank()) {
            entryStringBuilder.append(padding).append("  ").append(description).append(System.lineSeparator());
        }
        arguments.forEach(arg -> {
            entryStringBuilder.append(padding).append("  ").append("-").append(arg.getName()).append(System.lineSeparator());
            if (arg.getDescription().isBlank()) {
                return;
            }
            entryStringBuilder.append(padding).append("    ").append(arg.getDescription()).append(System.lineSeparator());
        });

        return entryStringBuilder.toString();
    }
}
