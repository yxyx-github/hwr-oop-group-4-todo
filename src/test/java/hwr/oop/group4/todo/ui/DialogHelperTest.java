package hwr.oop.group4.todo.ui;

import org.junit.jupiter.api.Test;

import java.io.*;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class DialogHelperTest {

    private String retrieveResultFrom(OutputStream outputStream) {
        return outputStream.toString();
    }

    private InputStream createInputStreamForInput(String input) {
        byte[] inputInBytes = input.getBytes();
        return new ByteArrayInputStream(inputInBytes);
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////
    // getValidIdFromUser()
    /////////////////////////////////////////////////////////////////////////////////////////////////////
    @Test
    void canGetId() {
        Scanner inputStream = new Scanner(createInputStreamForInput("a\n13\n4\n"));
        OutputStream outputStream = new ByteArrayOutputStream();
        DialogHelper dialogHelper = new DialogHelper(new PrintStream(outputStream), inputStream);

        int returnValue = dialogHelper.getValidIdFromUser("This is a prompt.", 12);
        String output = retrieveResultFrom(outputStream);

        assertThat(returnValue).isEqualTo(4);
        assertThat(output).isEqualTo("This is a prompt.Invalid index. (Must be a number)\n" +
                "This is a prompt.Invalid index. (Must be between (inclusive) 0 and 11)\n" +
                "This is a prompt.");
    }

    @Test
    void canRecognizeEdgeCase() {
        Scanner inputStream = new Scanner(createInputStreamForInput("a\n13\n0\n"));
        OutputStream outputStream = new ByteArrayOutputStream();
        DialogHelper dialogHelper = new DialogHelper(new PrintStream(outputStream), inputStream);

        Integer returnValue = dialogHelper.getValidIdFromUser("This is a prompt.", 0);
        String output = retrieveResultFrom(outputStream);

        assertThat(returnValue).isEqualTo(null);
        assertThat(output).isEqualTo("");
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////
    // getMenuSelectionFromUser()
    /////////////////////////////////////////////////////////////////////////////////////////////////////
    @Test
    void canPrintAMenu() {
        Scanner inputStream = new Scanner(createInputStreamForInput("a\nadd\n"));
        OutputStream outputStream = new ByteArrayOutputStream();
        DialogHelper dialogHelper = new DialogHelper(new PrintStream(outputStream), inputStream);

        String message = "This is a message.";
        String inputPrefix = "input> ";

        Map<String, String> options = new HashMap<>();
        options.put("add", "add something");
        options.put("delete", "delete something");

        String returnValue = dialogHelper.getMenuSelectionFromUser(message, inputPrefix, options);
        String output = retrieveResultFrom(outputStream);

        assertThat(returnValue).isEqualTo("add");
        assertThat(output).isEqualTo( "This is a message.\n" +
                "                           add - add something\n" +
                "                        delete - delete something\n" +
                "input> input> ");
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////
    // getYesNoFromUser()
    /////////////////////////////////////////////////////////////////////////////////////////////////////
    @Test
    void canGetDefaultYes() {
        Scanner inputStream = new Scanner(createInputStreamForInput("a\n13\n\n"));
        OutputStream outputStream = new ByteArrayOutputStream();
        DialogHelper dialogHelper = new DialogHelper(new PrintStream(outputStream), inputStream);

        boolean returnValue = dialogHelper.getYesNoFromUser("To be, or not to be?", true);
        String output = retrieveResultFrom(outputStream);

        assertThat(returnValue).isEqualTo(true);
        assertThat(output).isEqualTo("To be, or not to be?\n" +
                "Answer y/Y/yes or n/N/no (leave empty for: yes): To be, or not to be?\n" +
                "Answer y/Y/yes or n/N/no (leave empty for: yes): To be, or not to be?\n" +
                "Answer y/Y/yes or n/N/no (leave empty for: yes): ");
    }

    @Test
    void canGetDefaultNo() {
        Scanner inputStream = new Scanner(createInputStreamForInput("a\n13\n\n"));
        OutputStream outputStream = new ByteArrayOutputStream();
        DialogHelper dialogHelper = new DialogHelper(new PrintStream(outputStream), inputStream);

        boolean returnValue = dialogHelper.getYesNoFromUser("To be, or not to be?", false);
        String output = retrieveResultFrom(outputStream);

        assertThat(returnValue).isEqualTo(false);
        assertThat(output).isEqualTo("To be, or not to be?\n" +
                "Answer y/Y/yes or n/N/no (leave empty for: no): To be, or not to be?\n" +
                "Answer y/Y/yes or n/N/no (leave empty for: no): To be, or not to be?\n" +
                "Answer y/Y/yes or n/N/no (leave empty for: no): ");
    }

    @Test
    void canGetNo() {
        Scanner inputStream = new Scanner(createInputStreamForInput("a\n13\nNO\n"));
        OutputStream outputStream = new ByteArrayOutputStream();
        DialogHelper dialogHelper = new DialogHelper(new PrintStream(outputStream), inputStream);

        boolean returnValue = dialogHelper.getYesNoFromUser("To be, or not to be?", true);
        String output = retrieveResultFrom(outputStream);

        assertThat(returnValue).isEqualTo(false);
        assertThat(output).isEqualTo("To be, or not to be?\n" +
                "Answer y/Y/yes or n/N/no (leave empty for: yes): To be, or not to be?\n" +
                "Answer y/Y/yes or n/N/no (leave empty for: yes): To be, or not to be?\n" +
                "Answer y/Y/yes or n/N/no (leave empty for: yes): ");
    }

    @Test
    void canGetYes() {
        Scanner inputStream = new Scanner(createInputStreamForInput("a\n13\nyes\n"));
        OutputStream outputStream = new ByteArrayOutputStream();
        DialogHelper dialogHelper = new DialogHelper(new PrintStream(outputStream), inputStream);

        boolean returnValue = dialogHelper.getYesNoFromUser("To be, or not to be?", false);
        String output = retrieveResultFrom(outputStream);

        assertThat(returnValue).isEqualTo(true);
        assertThat(output).isEqualTo("To be, or not to be?\n" +
                "Answer y/Y/yes or n/N/no (leave empty for: no): To be, or not to be?\n" +
                "Answer y/Y/yes or n/N/no (leave empty for: no): To be, or not to be?\n" +
                "Answer y/Y/yes or n/N/no (leave empty for: no): ");
    }

    @Test
    void canGetN() {
        Scanner inputStream = new Scanner(createInputStreamForInput("a\n13\nn\n"));
        OutputStream outputStream = new ByteArrayOutputStream();
        DialogHelper dialogHelper = new DialogHelper(new PrintStream(outputStream), inputStream);

        boolean returnValue = dialogHelper.getYesNoFromUser("To be, or not to be?", true);
        String output = retrieveResultFrom(outputStream);

        assertThat(returnValue).isEqualTo(false);
        assertThat(output).isEqualTo("To be, or not to be?\n" +
                "Answer y/Y/yes or n/N/no (leave empty for: yes): To be, or not to be?\n" +
                "Answer y/Y/yes or n/N/no (leave empty for: yes): To be, or not to be?\n" +
                "Answer y/Y/yes or n/N/no (leave empty for: yes): ");
    }

    @Test
    void canGetY() {
        Scanner inputStream = new Scanner(createInputStreamForInput("a\n13\nY\n"));
        OutputStream outputStream = new ByteArrayOutputStream();
        DialogHelper dialogHelper = new DialogHelper(new PrintStream(outputStream), inputStream);

        boolean returnValue = dialogHelper.getYesNoFromUser("To be, or not to be?", false);
        String output = retrieveResultFrom(outputStream);

        assertThat(returnValue).isEqualTo(true);
        assertThat(output).isEqualTo("To be, or not to be?\n" +
                "Answer y/Y/yes or n/N/no (leave empty for: no): To be, or not to be?\n" +
                "Answer y/Y/yes or n/N/no (leave empty for: no): To be, or not to be?\n" +
                "Answer y/Y/yes or n/N/no (leave empty for: no): ");
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////
    // getYesNoFromUser()
    /////////////////////////////////////////////////////////////////////////////////////////////////////

    @Test
    void canReturnDefaultValue() {
        Scanner inputStream = new Scanner(createInputStreamForInput("a\n13\n\n"));
        OutputStream outputStream = new ByteArrayOutputStream();
        DialogHelper dialogHelper = new DialogHelper(new PrintStream(outputStream), inputStream);

        LocalDateTime returnValue = dialogHelper.getLocalDateTimeFromUser("When is today?", true);
        String output = retrieveResultFrom(outputStream);

        assertThat(returnValue.getYear()).isEqualTo(LocalDateTime.now().getYear());
        assertThat(returnValue.getMonth()).isEqualTo(LocalDateTime.now().getMonth());
        assertThat(returnValue.getDayOfMonth()).isEqualTo(LocalDateTime.now().getDayOfMonth());

        assertThat(output).isEqualTo("When is today?\n" +
                "The current date/time will be used if you leave this empty.\n" +
                "Enter a date/time formatted as 'dd.mm.yyyy' or 'dd.mm.yyyy hh:mm': When is today?\n" +
                "The current date/time will be used if you leave this empty.\n" +
                "Enter a date/time formatted as 'dd.mm.yyyy' or 'dd.mm.yyyy hh:mm': When is today?\n" +
                "The current date/time will be used if you leave this empty.\n" +
                "Enter a date/time formatted as 'dd.mm.yyyy' or 'dd.mm.yyyy hh:mm': ");
    }

    @Test
    void canAcceptDate() {
        Scanner inputStream = new Scanner(createInputStreamForInput("a\n12.15.2023\n12.12.2012\n"));
        OutputStream outputStream = new ByteArrayOutputStream();
        DialogHelper dialogHelper = new DialogHelper(new PrintStream(outputStream), inputStream);

        LocalDateTime returnValue = dialogHelper.getLocalDateTimeFromUser("When is today?", false);
        String output = retrieveResultFrom(outputStream);

        LocalDateTime expected = LocalDateTime.of(2012,12,12,12,12);
        assertThat(returnValue.getYear()).isEqualTo(expected.getYear());
        assertThat(returnValue.getMonth()).isEqualTo(expected.getMonth());
        assertThat(returnValue.getDayOfMonth()).isEqualTo(expected.getDayOfMonth());

        assertThat(output).isEqualTo("When is today?\n" +
                "Enter a date/time formatted as 'dd.mm.yyyy' or 'dd.mm.yyyy hh:mm': When is today?\n" +
                "Enter a date/time formatted as 'dd.mm.yyyy' or 'dd.mm.yyyy hh:mm': When is today?\n" +
                "Enter a date/time formatted as 'dd.mm.yyyy' or 'dd.mm.yyyy hh:mm': ");
    }

    @Test
    void canAcceptDateWithTime() {
        Scanner inputStream = new Scanner(createInputStreamForInput("\n12..2023\n12.12.2012 12:13\n"));
        OutputStream outputStream = new ByteArrayOutputStream();
        DialogHelper dialogHelper = new DialogHelper(new PrintStream(outputStream), inputStream);

        LocalDateTime returnValue = dialogHelper.getLocalDateTimeFromUser("When is today?", false);
        String output = retrieveResultFrom(outputStream);

        LocalDateTime expected = LocalDateTime.of(2012,12,12,12,13);
        assertThat(returnValue.getYear()).isEqualTo(expected.getYear());
        assertThat(returnValue.getMonth()).isEqualTo(expected.getMonth());
        assertThat(returnValue.getDayOfMonth()).isEqualTo(expected.getDayOfMonth());
        assertThat(returnValue.getHour()).isEqualTo(expected.getHour());
        assertThat(returnValue.getMinute()).isEqualTo(expected.getMinute());

        assertThat(output).isEqualTo("When is today?\n" +
                "Enter a date/time formatted as 'dd.mm.yyyy' or 'dd.mm.yyyy hh:mm': When is today?\n" +
                "Enter a date/time formatted as 'dd.mm.yyyy' or 'dd.mm.yyyy hh:mm': When is today?\n" +
                "Enter a date/time formatted as 'dd.mm.yyyy' or 'dd.mm.yyyy hh:mm': ");
    }

}
