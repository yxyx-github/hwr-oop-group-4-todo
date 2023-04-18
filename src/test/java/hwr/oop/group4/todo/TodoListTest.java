package hwr.oop.group4.todo;

import hwr.oop.group4.todo.builder.TaskBuilder;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TodoListTest {

    @Test
    void emptyOnConstruction() {
        final TodoList todo = new TodoList();

        assertEquals(0, todo.getMaybeList().size());
        assertEquals(0, todo.getProjects().size());
        assertEquals(0, todo.getInTray().size());
        assertEquals(0, todo.getLoseTasks().size());
    }

    @Test
    void inTray() {
        final TodoList todo = new TodoList();

        todo.addIdea(new Idea("new Idea"));
        todo.addIdea(new Idea("new Idea"));
        todo.addIdea(new Idea("another Idea"));

        assertEquals(2, todo.getInTray().size());
        assertTrue(todo.getInTray().contains(new Idea("new Idea")));
        assertTrue(todo.getInTray().contains(new Idea("another Idea")));
    }

    @Test
    void loseTasks() {
        final TodoList todo = new TodoList();
        final Task taskA = new TaskBuilder().setName("task").build();
        final Task taskB = new TaskBuilder().setName("another task").build();

        todo.addLoseTask(taskA);
        todo.addLoseTask(taskA);
        todo.addLoseTask(taskB);

        assertEquals(2, todo.getLoseTasks().size());
        assertTrue(todo.getLoseTasks().contains(taskA));
        assertTrue(todo.getLoseTasks().contains(taskB));
    }

    @Test
    void projects() {
        final TodoList todo = new TodoList();
        final Project projectA = new Project("name", "desc");
        final Project projectB = new Project("name", "desc");

        todo.addProject(projectA);
        todo.addProject(projectA);
        todo.addProject(projectB);

        assertEquals(3, todo.getProjects().size());
        assertEquals(projectA, todo.getProjects().get(0));
        assertEquals(projectA, todo.getProjects().get(1));
        assertEquals(projectB, todo.getProjects().get(2));
    }

    @Test
    void someDayMaybe() {
        final TodoList todo = new TodoList();
        final Task taskA = new TaskBuilder().setName("task").build();
        final Task taskB = new TaskBuilder().setName("another task").build();

        todo.addSomedayMaybeTask(taskA);
        todo.addSomedayMaybeTask(taskA);
        todo.addSomedayMaybeTask(taskB);

        assertEquals(2, todo.getMaybeList().size());
        assertTrue(todo.getMaybeList().contains(taskA));
        assertTrue(todo.getMaybeList().contains(taskB));
    }

}