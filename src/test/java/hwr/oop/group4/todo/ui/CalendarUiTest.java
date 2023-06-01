package hwr.oop.group4.todo.ui;

import hwr.oop.group4.todo.core.Project;
import hwr.oop.group4.todo.core.TodoList;
import hwr.oop.group4.todo.ui.controller.ConsoleController;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.setLenientDateParsing;

public class CalendarUiTest {

    private final String calendarMenuOutput =
            "[1m<==== Project Menu ====>[0m" + System.lineSeparator() +
                    System.lineSeparator() +
                    "Commands: " + System.lineSeparator() +
                    "  today" + System.lineSeparator() +
                    "  nextWeek" + System.lineSeparator() +
                    "  lastWeek" + System.lineSeparator() +
                    "  back" + System.lineSeparator() +
                    "    Return to the previous menu" + System.lineSeparator() +
                    System.lineSeparator();

    private String retrieveResultFrom(OutputStream outputStream) {
        return outputStream.toString();
    }

    private InputStream createInputStreamForInput(String input) {
        byte[] inputInBytes = input.getBytes();
        return new ByteArrayInputStream(inputInBytes);
    }
}
