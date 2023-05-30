package hwr.oop.group4.todo.persistence;

import hwr.oop.group4.todo.commons.exceptions.PersistenceRuntimeException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class FileAdapter implements LoadPersistenceAdapter, SavePersistenceAdapter {

    @Override
    public void save(Persistable data, File file) {
        try {
            file.createNewFile();
            try (FileWriter writer = new FileWriter(file)) {
                writer.write(data.exportAsString());
            }
        } catch (IOException e) {
            throw new PersistenceRuntimeException("Cannot create or write file", e);
        }
    }

    @Override
    public Persistable load(Persistable data, File file) {
        StringBuilder output = new StringBuilder();
        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                output.append(scanner.nextLine());
            }
        } catch (FileNotFoundException e) {
            throw new PersistenceRuntimeException("Cannot read file", e);
        }
        data.importFromString(output.toString());
        return data;
    }
}
