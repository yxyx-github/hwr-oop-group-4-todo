package hwr.oop.group4.todo.ui.controller;

import hwr.oop.group4.todo.commons.exceptions.TodoUiRuntimeException;
import hwr.oop.group4.todo.ui.controller.command.Command;
import hwr.oop.group4.todo.ui.controller.command.CommandArgument;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ConsoleControllerTest {

    private String retrieveResultFrom(OutputStream outputStream) {
        return outputStream.toString();
    }

    private InputStream createInputStreamForInput(String input) {
        byte[] inputInBytes = input.getBytes();
        return new ByteArrayInputStream(inputInBytes);
    }

    @Test
    void inputPrompt() {
        final InputStream inputStream = createInputStreamForInput("test input");
        final OutputStream outputStream = new ByteArrayOutputStream();
        final ConsoleController consoleController = new ConsoleController(outputStream, inputStream);

        final Optional<String> input = consoleController.input(List.of("pre1", "pre2", "3"), "prompt");
        final String output = retrieveResultFrom(outputStream);
        assertThat(output).isEqualTo("prompt" + System.lineSeparator() + "pre1/pre2/3:> ");
        assertThat(input).hasValue("test input");
    }

    @Test
    void input() {
        final InputStream inputStream = createInputStreamForInput("test input");
        final OutputStream outputStream = new ByteArrayOutputStream();
        final ConsoleController consoleController = new ConsoleController(outputStream, inputStream);

        final Optional<String> input = consoleController.input(List.of("pre1", "pre2", "3"));
        final String output = retrieveResultFrom(outputStream);
        assertThat(output).isEqualTo("pre1/pre2/3:> ");
        assertThat(input).hasValue("test input");
    }

    @Test
    void emptyInput() {
        final InputStream inputStream = createInputStreamForInput("");
        final OutputStream outputStream = new ByteArrayOutputStream();
        final ConsoleController consoleController = new ConsoleController(outputStream, inputStream);

        final Optional<String> input = consoleController.input(List.of(""));
        assertThat(input).isEmpty();
    }

    @Test
    void inputOption() {
        final InputStream inputStream = createInputStreamForInput("testCmd");
        final OutputStream outputStream = new ByteArrayOutputStream();
        final ConsoleController consoleController = new ConsoleController(outputStream, inputStream);

        consoleController.inputOptions(List.of("pre1", "pre2"), List.of(
                        new Command("notExecuted", args -> consoleController.output("isn't executed")),
                        new Command("testCmd", args -> consoleController.output("test cmd"))
                ),
                new Command("wrong", args -> {})
        );
        final String output = retrieveResultFrom(outputStream);
        assertThat(output).isEqualTo("pre1/pre2:> test cmd");
    }

    @Test
    void inputOptionsNoInput() {
        final InputStream inputStream = createInputStreamForInput("");
        final OutputStream outputStream = new ByteArrayOutputStream();
        final ConsoleController consoleController = new ConsoleController(outputStream, inputStream);
        final List<String> prefixes = List.of("");

        assertThatThrownBy(() -> consoleController.inputOptions(prefixes, null, null))
                .isInstanceOf(TodoUiRuntimeException.class)
                .hasMessage("Input is expected")
                .hasCause(null);

    }

    @Test
    void inputOptionWithArguments() {
        final InputStream inputStream = createInputStreamForInput("test -a asd -b asdsad -c");
        final OutputStream outputStream = new ByteArrayOutputStream();
        final ConsoleController consoleController = new ConsoleController(outputStream, inputStream);

        consoleController.inputOptions(List.of("pre1", "pre2"),
                List.of(
                        new Command("test", arguments ->
                                consoleController.output(
                                        String.valueOf(arguments.stream()
                                                .filter(arg -> arg.name().equals("a")).findAny().get().value())
                                )
                        )
                ),
                new Command("wrong", args -> {})
        );

        final String output = retrieveResultFrom(outputStream);
        assertThat(output).isEqualTo("pre1/pre2:> asd");
    }

    @Test
    void output() {
        final InputStream inputStream = createInputStreamForInput("");
        final OutputStream outputStream = new ByteArrayOutputStream();
        final ConsoleController consoleController =  new ConsoleController(outputStream, inputStream);

        consoleController.output("Test Output String");
        final String output = retrieveResultFrom(outputStream);
        assertThat(output).isEqualTo("Test Output String");
    }

    @Test
    void outputLine() {
        final InputStream inputStream = createInputStreamForInput("");
        final OutputStream outputStream = new ByteArrayOutputStream();
        final ConsoleController consoleController =  new ConsoleController(outputStream, inputStream);

        consoleController.outputLine("Test Output Line");
        final String output = retrieveResultFrom(outputStream);
        assertThat(output).isEqualTo("Test Output Line" + System.lineSeparator());
    }

    @ParameterizedTest
    @ValueSource(booleans = {false, true})
    void inputBoolDefaultWithNoInputTest(boolean defaultValue) {
        final InputStream inputStream = createInputStreamForInput("");
        final OutputStream outputStream = new ByteArrayOutputStream();
        final ConsoleController consoleController = new ConsoleController(outputStream, inputStream);

        final boolean returnValue = consoleController.inputBool(List.of(""), "Eingabe.", defaultValue);
        assertThat(returnValue).isEqualTo(defaultValue);
    }

    @ParameterizedTest
    @ValueSource(booleans = {false, true})
    void inputBoolDefaultWithBlankInputTest(boolean defaultValue) {
        final InputStream inputStream = createInputStreamForInput("    " + System.lineSeparator());
        final OutputStream outputStream = new ByteArrayOutputStream();
        final ConsoleController consoleController = new ConsoleController(outputStream, inputStream);

        final boolean returnValue = consoleController.inputBool(List.of(""), "Eingabe.", defaultValue);
        assertThat(returnValue).isEqualTo(defaultValue);
    }

    @ParameterizedTest
    @ValueSource(strings = {"no", "n", "N"})
    void inputBoolNoTest(String inputString) {
        final InputStream inputStream = createInputStreamForInput("lorem" + System.lineSeparator() + inputString + System.lineSeparator());
        final OutputStream outputStream = new ByteArrayOutputStream();
        final ConsoleController consoleController = new ConsoleController(outputStream, inputStream);

        final boolean returnValue = consoleController.inputBool(List.of(""), "Eingabe.", true);
        assertThat(returnValue).isFalse();
    }

    @ParameterizedTest
    @ValueSource(strings = {"yes", "y", "Y"})
    void inputBoolYesTest(String inputString) {
        final InputStream inputStream = createInputStreamForInput("lorem" + System.lineSeparator() + inputString + System.lineSeparator());
        final OutputStream outputStream = new ByteArrayOutputStream();
        final ConsoleController consoleController = new ConsoleController(outputStream, inputStream);

        final boolean returnValue = consoleController.inputBool(List.of(""), "Eingabe.", true);
        assertThat(returnValue).isTrue();
    }

    @Test
    void inputBoolPromptTest() {
        final InputStream inputStream = createInputStreamForInput(System.lineSeparator());
        final OutputStream outputStream = new ByteArrayOutputStream();
        final ConsoleController consoleController = new ConsoleController(outputStream, inputStream);

        consoleController.inputBool(List.of(""), "Eingabe.", true);
        assertThat(retrieveResultFrom(outputStream)).isEqualTo("Eingabe."+ System.lineSeparator() +
                        "Answer y/Y/yes or n/N/no (leave empty for: yes)." + System.lineSeparator() +
                        ":> "
        );
    }

    @ParameterizedTest
    @ValueSource(ints = {-10, -3, 0, 10})
    void inputIntDefault(int defaultValue) {
        final InputStream inputStream = createInputStreamForInput("");
        final OutputStream outputStream = new ByteArrayOutputStream();
        final ConsoleController consoleController = new ConsoleController(outputStream, inputStream);
        final int input = consoleController.inputInt(List.of(""), "", defaultValue);
        assertThat(input).isEqualTo(defaultValue);
    }

    @ParameterizedTest
    @ValueSource(strings = {"-10", "-3", "0", "10"})
    void inputInt(String input) {
        final InputStream inputStream = createInputStreamForInput(input);
        final OutputStream outputStream = new ByteArrayOutputStream();
        final ConsoleController consoleController = new ConsoleController(outputStream, inputStream);
        final int integer = consoleController.inputInt(List.of(""));
        assertThat(integer).isEqualTo(Integer.parseInt(input));
    }

    @Test
    void inputIntPrompt() {
        final InputStream inputStream = createInputStreamForInput("");
        final OutputStream outputStream = new ByteArrayOutputStream();
        final ConsoleController consoleController = new ConsoleController(outputStream, inputStream);
        consoleController.inputInt(List.of(""), "Prompt");
        assertThat(retrieveResultFrom(outputStream)).isEqualTo("Prompt" + System.lineSeparator() +
                "Enter a whole number [default: 0]: :> ");
    }

    @Test
    void inputUnsupportedValue() {
        final InputStream inputStream = createInputStreamForInput("4000000000" + System.lineSeparator() + "40");
        final OutputStream outputStream = new ByteArrayOutputStream();
        final ConsoleController consoleController = new ConsoleController(outputStream, inputStream);
        final int input = consoleController.inputInt(List.of(""));
        assertThat(input).isEqualTo(40);
    }

    @Test
    void inputDateDefaultTest() {
        final InputStream inputStream = createInputStreamForInput("");
        final OutputStream outputStream = new ByteArrayOutputStream();
        final ConsoleController consoleController = new ConsoleController(outputStream, inputStream);

        final LocalDateTime returnValue = consoleController.inputDate(List.of(""), "Prompt.");

        final LocalDateTime now = LocalDateTime.now();
        assertThat(Duration.between(returnValue, now).toSeconds()).isLessThanOrEqualTo(5);
    }

    @Test
    void inputDateTest() {
        final InputStream inputStream = createInputStreamForInput("1123432" + System.lineSeparator() + "12.12.1212");
        final OutputStream outputStream = new ByteArrayOutputStream();
        final ConsoleController consoleController = new ConsoleController(outputStream, inputStream);

        final LocalDateTime returnValue = consoleController.inputDate(List.of(""));
        final LocalDateTime value = LocalDateTime.of(1212, 12, 12, 0, 0);
        assertThat(returnValue).isEqualTo(value);
    }

    @Test
    void inputDateTimeTest() {
        final InputStream inputStream = createInputStreamForInput("12.12.1212 12:12");
        final OutputStream outputStream = new ByteArrayOutputStream();
        final ConsoleController consoleController = new ConsoleController(outputStream, inputStream);

        final LocalDateTime returnValue = consoleController.inputDate(List.of(""), "Prompt.");

        LocalDateTime value = LocalDateTime.of(1212, 12, 12, 12, 12);
        assertThat(returnValue).isEqualTo(value);
    }

    @Test
    void inputDateOutputTest() {
        final InputStream inputStream = createInputStreamForInput("");
        final OutputStream outputStream = new ByteArrayOutputStream();
        final ConsoleController consoleController = new ConsoleController(outputStream, inputStream);

        consoleController.inputDate(List.of(""), "Prompt.");
        assertThat(retrieveResultFrom(outputStream)).isEqualTo("Prompt." + System.lineSeparator() +
                "Enter a date/time formatted as 'dd.mm.yyyy' or 'dd.mm.yyyy hh:mm': :> "
        );
    }

    @Test
    void canCallWithValidId() {
        final InputStream inputStream = createInputStreamForInput("");
        final OutputStream outputStream = new ByteArrayOutputStream();
        final ConsoleController consoleController = new ConsoleController(outputStream, inputStream);

        class Method {
            private boolean wasCalled;
            public Method() { wasCalled = false; }
            public void call(Collection<CommandArgument> args) { wasCalled = true; }
            public boolean methodWasCalled() { return wasCalled; }
        }
        Method method = new Method();

        final Collection<CommandArgument> args = List.of(new CommandArgument("id", "11"));
        consoleController.callWithValidId(true, 12, args, method::call);

        assertThat(method.methodWasCalled()).isTrue();
        assertThat(retrieveResultFrom(outputStream)).isEmpty();
    }

    @Test
    void cantCallWithInvalidId() {
        final InputStream inputStream = createInputStreamForInput("");
        final OutputStream outputStream = new ByteArrayOutputStream();
        final ConsoleController consoleController = new ConsoleController(outputStream, inputStream);

        class Method {
            private boolean wasCalled;
            public Method() { wasCalled = false; }
            public void call(Collection<CommandArgument> args) { wasCalled = true; }
            public boolean methodWasCalled() { return wasCalled; }
        }
        Method method = new Method();

        final Collection<CommandArgument> args = List.of(new CommandArgument("id", "11"));
        consoleController.callWithValidId(true, 1, args, method::call);

        assertThat(method.methodWasCalled()).isFalse();
        assertThat(retrieveResultFrom(outputStream)).isEqualTo("ID parameter is invalid." + System.lineSeparator());
    }

}
