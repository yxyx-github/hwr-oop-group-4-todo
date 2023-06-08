package hwr.oop.group4.todo.core;

import java.util.*;

public class TodoList {

    private final List<Project> projects = new ArrayList<>();
    private final Set<Idea> inTray = new HashSet<>();
    private final Set<Task> loseTasks = new HashSet<>();
    private final List<Project> somedayMaybe = new ArrayList<>();


    public List<Project> getProjects() {
        return projects;
    }

    public Set<Idea> getInTray() {
        return inTray;
    }

    public Set<Task> getLoseTasks() {
        return loseTasks;
    }

    public List<Project> getMaybeList() {
        return somedayMaybe;
    }

    public void addIdea(Idea idea) {
        inTray.add(idea);
    }

    public void removeIdea(Idea idea) {
        inTray.remove(idea);
    }

    public void addLoseTask(Task task) {
        loseTasks.add(task);
    }

    public void removeLoseTask(Task task) {
        loseTasks.remove(task);
    }

    public void addSomedayMaybeProject(Project project) {
        somedayMaybe.add(project);
    }

    public void removeSomedayMaybeProject(Project project) {
        somedayMaybe.remove(project);
    }

    public void addProject(Project project) {
        projects.add(project);
    }

    public void removeProject(Project project) {
        projects.remove(project);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TodoList todoList = (TodoList) o;
        return Objects.equals(projects, todoList.projects) && Objects.equals(inTray, todoList.inTray) && Objects.equals(loseTasks, todoList.loseTasks) && Objects.equals(somedayMaybe, todoList.somedayMaybe);
    }

    @Override
    public int hashCode() {
        return Objects.hash(projects, inTray, loseTasks, somedayMaybe);
    }
}
