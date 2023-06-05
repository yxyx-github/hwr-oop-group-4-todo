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

class CalendarUiTest {

    private  final LocalDate date = LocalDate.now();
    private final LocalDate monday = date.minusDays(date.getDayOfWeek().getValue() - 1);
    private final String calendarMenuOutput =
            "[1m<==== Calendar Menu ====>[0m" + System.lineSeparator() +
                    System.lineSeparator() +
                    "Commands: " + System.lineSeparator() +
                    "  today" + System.lineSeparator() +
                    "  nextWeek" + System.lineSeparator() +
                    "  lastWeek" + System.lineSeparator() +
                    "  back" + System.lineSeparator() +
                    "    Return to the previous menu" + System.lineSeparator() +
                    System.lineSeparator();

    private final String todayCalendar =
            "| Mon - " + monday.format(DateTimeFormatter.ofPattern("dd.MM.yy")) +
            "  | Tue - " + monday.plusDays(1).format(DateTimeFormatter.ofPattern("dd.MM.yy")) +
            "  | Wed - " + monday.plusDays(2).format(DateTimeFormatter.ofPattern("dd.MM.yy")) +
            "  | Thu - " + monday.plusDays(3).format(DateTimeFormatter.ofPattern("dd.MM.yy")) +
            "  | Fri - " + monday.plusDays(4).format(DateTimeFormatter.ofPattern("dd.MM.yy")) +
            "  | Sat - " + monday.plusDays(5).format(DateTimeFormatter.ofPattern("dd.MM.yy")) +
            "  | Sun - " + monday.plusDays(6).format(DateTimeFormatter.ofPattern("dd.MM.yy")) + "  |" + System.lineSeparator() +
            "===============================================================================================================================" + System.lineSeparator() +
            "|     TestProject |     TestProject |     TestProject |     TestProject |                 |                 |                 |" + System.lineSeparator() +
            "|             ICE |             ICE |             ICE |             ICE |             ICE |             ICE |             ICE |" + System.lineSeparator();

    private final String nextWeekCalendar =
            "| Mon - " + monday.plusDays(7).format(DateTimeFormatter.ofPattern("dd.MM.yy")) +
            "  | Tue - " + monday.plusDays(8).format(DateTimeFormatter.ofPattern("dd.MM.yy")) +
            "  | Wed - " + monday.plusDays(9).format(DateTimeFormatter.ofPattern("dd.MM.yy")) +
            "  | Thu - " + monday.plusDays(10).format(DateTimeFormatter.ofPattern("dd.MM.yy")) +
            "  | Fri - " + monday.plusDays(11).format(DateTimeFormatter.ofPattern("dd.MM.yy")) +
            "  | Sat - " + monday.plusDays(12).format(DateTimeFormatter.ofPattern("dd.MM.yy")) +
            "  | Sun - " + monday.plusDays(13).format(DateTimeFormatter.ofPattern("dd.MM.yy")) + "  |" + System.lineSeparator() +
            "===============================================================================================================================" + System.lineSeparator() +
            "|                 |                 |                 |                 |                 |                 |                 |" + System.lineSeparator() +
            "|             ICE |             ICE |             ICE |                 |                 |                 |                 |" + System.lineSeparator();

    private final String lastWeekCalendar =
            "| Mon - " + monday.minusDays(7).format(DateTimeFormatter.ofPattern("dd.MM.yy")) +
            "  | Tue - " + monday.minusDays(6).format(DateTimeFormatter.ofPattern("dd.MM.yy")) +
            "  | Wed - " + monday.minusDays(5).format(DateTimeFormatter.ofPattern("dd.MM.yy")) +
            "  | Thu - " + monday.minusDays(4).format(DateTimeFormatter.ofPattern("dd.MM.yy")) +
            "  | Fri - " + monday.minusDays(3).format(DateTimeFormatter.ofPattern("dd.MM.yy")) +
            "  | Sat - " + monday.minusDays(2).format(DateTimeFormatter.ofPattern("dd.MM.yy")) +
            "  | Sun - " + monday.minusDays(1).format(DateTimeFormatter.ofPattern("dd.MM.yy")) + "  |" + System.lineSeparator() +
            "===============================================================================================================================" + System.lineSeparator() +
            "|                 |                 |                 |                 |                 |                 |                 |" + System.lineSeparator() +
            "|                 |                 |                 |                 |                 |             ICE |             ICE |" + System.lineSeparator();

    private String retrieveResultFrom(OutputStream outputStream) {
        return outputStream.toString();
    }

    private InputStream createInputStreamForInput(String input) {
        byte[] inputInBytes = input.getBytes();
        return new ByteArrayInputStream(inputInBytes);
    }

    private TodoList getExampleTodoList() {
        TodoList todoList = new TodoList();

        todoList.addProject(new Project.ProjectBuilder()
                .name("TestProject")
                .description("Bla")
                .begin(LocalDateTime.of(monday.getYear(), monday.getMonth(), monday.getDayOfMonth(), 8, 45))
                .end(LocalDateTime.of(monday.getYear(), monday.plusDays(3).getMonth(), monday.plusDays(3).getDayOfMonth(), 12, 15))
                .build());

        todoList.addProject(new Project.ProjectBuilder()
                .name("ICE")
                .description("2nd Gen")
                .begin(LocalDateTime.of(monday.getYear(), monday.minusDays(2).getMonth(), monday.minusDays(2).getDayOfMonth(), 5, 45))
                .end(LocalDateTime.of(monday.getYear(), monday.plusDays(9).getMonth(), monday.plusDays(9).getDayOfMonth(), 0, 1))
                .build());

        return todoList;
    }

    @Test
    void canListCalendar() {
        InputStream inputStream = createInputStreamForInput("today" + System.lineSeparator() + "back" + System.lineSeparator());
        OutputStream outputStream = new ByteArrayOutputStream();

        CalendarUi ui = new CalendarUi(new ConsoleController(outputStream, inputStream));
        ui.menu(getExampleTodoList());

        String output = retrieveResultFrom(outputStream);

        assertThat(output).isEqualTo(calendarMenuOutput + todayCalendar + "calendar:> " + todayCalendar + "calendar:> ");
    }

    @Test
    void nextWeek() {
        InputStream inputStream = createInputStreamForInput("nextWeek" + System.lineSeparator() + "back" + System.lineSeparator());
        OutputStream outputStream = new ByteArrayOutputStream();

        CalendarUi ui = new CalendarUi(new ConsoleController(outputStream, inputStream));
        ui.menu(getExampleTodoList());

        String output = retrieveResultFrom(outputStream);
        assertThat(output).isEqualTo(calendarMenuOutput + todayCalendar + "calendar:> " + nextWeekCalendar + "calendar:> ");
    }

    @Test
    void lastWeek() {
        InputStream inputStream = createInputStreamForInput("lastWeek" + System.lineSeparator() + "back" + System.lineSeparator());
        OutputStream outputStream = new ByteArrayOutputStream();

        CalendarUi ui = new CalendarUi(new ConsoleController(outputStream, inputStream));
        ui.menu(getExampleTodoList());

        String output = retrieveResultFrom(outputStream);
        assertThat(output).isEqualTo(calendarMenuOutput + todayCalendar + "calendar:> " + lastWeekCalendar + "calendar:> ");
    }

    @Test
    void wrongInput() {
        InputStream inputStream = createInputStreamForInput("ThisMightBeAWrongInput" + System.lineSeparator() + "back" + System.lineSeparator());
        OutputStream outputStream = new ByteArrayOutputStream();

        CalendarUi ui = new CalendarUi(new ConsoleController(outputStream, inputStream));
        ui.menu(getExampleTodoList());

        String output = retrieveResultFrom(outputStream);
        assertThat(output).isEqualTo(calendarMenuOutput + todayCalendar + "calendar:> " + todayCalendar + "calendar:> ");
    }
}
