package hwr.oop.group4.todo.persistence;

public interface Persistable<T> {
    String exportAsString(T data);
    T importFromString(String dataString);
}
