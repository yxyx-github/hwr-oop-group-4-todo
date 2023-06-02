package hwr.oop.group4.todo.ui;

import hwr.oop.group4.todo.core.Project;
import hwr.oop.group4.todo.core.TodoList;
import hwr.oop.group4.todo.ui.controller.ConsoleController;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.time.LocalDateTime;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

//System date is manipulated for these tests
class CalendarUiTest {

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
            "| Mon - 29.05.23  | Tue - 30.05.23  | Wed - 31.05.23  | Thu - 01.06.23  | Fri - 02.06.23  | Sat - 03.06.23  | Sun - 04.06.23  |" + System.lineSeparator() +
                    "===============================================================================================================================" + System.lineSeparator() +
                    "|     TestProject |     TestProject |     TestProject |     TestProject |     TestProject |                 |                 |" + System.lineSeparator() +
                    "|             ICE |             ICE |             ICE |             ICE |             ICE |             ICE |             ICE |" + System.lineSeparator();

    private final String nextWeekCalendar =
            "| Mon - 05.06.23  | Tue - 06.06.23  | Wed - 07.06.23  | Thu - 08.06.23  | Fri - 09.06.23  | Sat - 10.06.23  | Sun - 11.06.23  |" + System.lineSeparator() +
            "===============================================================================================================================" + System.lineSeparator() +
            "|                 |                 |                 |                 |                 |                 |                 |" + System.lineSeparator() +
            "|             ICE |                 |                 |                 |                 |                 |                 |" + System.lineSeparator();

    private final String lastWeekCalendar =
            "| Mon - 22.05.23  | Tue - 23.05.23  | Wed - 24.05.23  | Thu - 25.05.23  | Fri - 26.05.23  | Sat - 27.05.23  | Sun - 28.05.23  |" + System.lineSeparator() +
            "===============================================================================================================================" + System.lineSeparator() +
            "|                 |                 |                 |     TestProject |     TestProject |     TestProject |     TestProject |" + System.lineSeparator() +
            "|             ICE |             ICE |             ICE |             ICE |             ICE |             ICE |             ICE |" + System.lineSeparator();

    private String retrieveResultFrom(OutputStream outputStream) {
        return outputStream.toString();
    }

    private InputStream createInputStreamForInput(String input) {
        byte[] inputInBytes = input.getBytes();
        return new ByteArrayInputStream(inputInBytes);
    }

    private TodoList getExampleTodoList(boolean addExampleProjects) {
        TodoList todoList = new TodoList();

        if (!addExampleProjects) {
            return todoList;
        }

        todoList.addProject(new Project.ProjectBuilder()
                .name("TestProject")
                .description("Bla")
                .begin(LocalDateTime.of(2023, 5, 25, 8, 45))
                .end(LocalDateTime.of(2023, 6, 2, 12, 15))
                .build()
        );
        todoList.addProject(new Project.ProjectBuilder()
                .name("ICE")
                .description("2nd Gen")
                .begin(LocalDateTime.of(2023, 5, 22, 5, 45))
                .end(LocalDateTime.of(2023, 6, 5, 0, 1))
                .build()
        );

        return todoList;
    }

    @Test
    void canListCalendar() {
        InputStream inputStream = createInputStreamForInput("today" + System.lineSeparator() + "back" + System.lineSeparator());
        OutputStream outputStream = new ByteArrayOutputStream();

        CalendarUi ui = new CalendarUi(new ConsoleController(outputStream, inputStream));
        ui.menu(getExampleTodoList(true));

        String output = retrieveResultFrom(outputStream);

        assertThat(output).isEqualTo(calendarMenuOutput + todayCalendar + "calendar:> " + todayCalendar + "calendar:> ");
    }

    @Test
    void nextWeek() {
        InputStream inputStream = createInputStreamForInput("nextWeek" + System.lineSeparator() + "back" + System.lineSeparator());
        OutputStream outputStream = new ByteArrayOutputStream();

        CalendarUi ui = new CalendarUi(new ConsoleController(outputStream, inputStream));
        ui.menu(getExampleTodoList(true));

        String output = retrieveResultFrom(outputStream);
        assertThat(output).isEqualTo(calendarMenuOutput + todayCalendar + "calendar:> " + nextWeekCalendar + "calendar:> ");
    }

    @Test
    void lastWeek() {
        InputStream inputStream = createInputStreamForInput("lastWeek" + System.lineSeparator() + "back" + System.lineSeparator());
        OutputStream outputStream = new ByteArrayOutputStream();

        CalendarUi ui = new CalendarUi(new ConsoleController(outputStream, inputStream));
        ui.menu(getExampleTodoList(true));

        String output = retrieveResultFrom(outputStream);
        assertThat(output).isEqualTo(calendarMenuOutput + todayCalendar + "calendar:> " + lastWeekCalendar + "calendar:> ");
    }

    @Test
    void wrongInput() {
        InputStream inputStream = createInputStreamForInput("" + System.lineSeparator() + "back" + System.lineSeparator());
        OutputStream outputStream = new ByteArrayOutputStream();

        CalendarUi ui = new CalendarUi(new ConsoleController(outputStream, inputStream));
        ui.menu(getExampleTodoList(true));

        String output = retrieveResultFrom(outputStream);
        assertThat(output).isEqualTo(calendarMenuOutput + todayCalendar + "calendar:> " + todayCalendar + "calendar:> ");
    }
}
