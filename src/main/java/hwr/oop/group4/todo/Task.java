package hwr.oop.group4.todo;

import hwr.oop.group4.todo.builder.TaskBuilder;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Set;

public class Task {

    private String name;
    private String description;
    /**
     * higher value means higher priority
     */
    private int priority;
    private LocalDateTime deadline;
    private final Set<Tag> tags;
    private Status status = Status.OPEN;

    public Task(TaskBuilder taskBuilder) {
        name = taskBuilder.getName();
        description = taskBuilder.getDescription();
        priority = taskBuilder.getPriority();
        deadline = taskBuilder.getDeadline();
        tags = taskBuilder.getTags();
        if (taskBuilder.getProject() != null) {
            taskBuilder.getProject().addTask(this);
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public LocalDateTime getDeadline() {
        return deadline;
    }

    public void setDeadline(LocalDateTime deadline) {
        this.deadline = deadline;
    }

    public Set<Tag> getTags() {
        return tags;
    }

    public void addTag(Tag tag) {
        tags.add(tag);
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return priority == task.priority && Objects.equals(name, task.name) && Objects.equals(description, task.description)
                && Objects.equals(deadline, task.deadline) && Objects.equals(tags, task.tags) && status == task.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, description, priority, deadline, tags, status);
    }
}
