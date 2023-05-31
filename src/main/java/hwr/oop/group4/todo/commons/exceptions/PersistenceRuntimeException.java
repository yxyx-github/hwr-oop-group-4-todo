package hwr.oop.group4.todo.commons.exceptions;

public class PersistenceRuntimeException extends RuntimeException {

    public PersistenceRuntimeException() {
    }

    public PersistenceRuntimeException(String message) {
        super(message);
    }

    public PersistenceRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }

    public PersistenceRuntimeException(Throwable cause) {
        super(cause);
    }

    public PersistenceRuntimeException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
