package hwr.oop.group4.todo.persistence;

public interface Persistable<T> {
    String exportAsString();
    void importFromString(String dataString);
}
