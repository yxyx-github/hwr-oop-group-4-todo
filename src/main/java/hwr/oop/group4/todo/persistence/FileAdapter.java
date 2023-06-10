package hwr.oop.group4.todo.persistence;

import hwr.oop.group4.todo.commons.exceptions.PersistenceRuntimeException;
import hwr.oop.group4.todo.persistence.configuration.FileAdapterConfiguration;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class FileAdapter<T> implements LoadPersistenceAdapter<T, FileAdapterConfiguration>, SavePersistenceAdapter<T, FileAdapterConfiguration> {

    @Override
    public void save(Persistable<T> persistable, T data, FileAdapterConfiguration config) {
        File file = config.getFile()
                .orElseThrow(() -> new PersistenceRuntimeException("No file provided"));
        try {
            file.createNewFile();
        } catch (IOException e) {
            throw new PersistenceRuntimeException("Cannot create file", e);
        }

        try (FileWriter writer = new FileWriter(file)) {
            writer.write(persistable.exportAsString(data));
        } catch (IOException e) {
            throw new PersistenceRuntimeException("Cannot write file", e);
        }
    }

    @Override
    public T load(Persistable<T> persistable, FileAdapterConfiguration config) {
        File file = config.getFile()
                .orElseThrow(() -> new PersistenceRuntimeException("No file provided"));
        StringBuilder output = new StringBuilder();
        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                output.append(scanner.nextLine());
            }
        } catch (FileNotFoundException e) {
            throw new PersistenceRuntimeException("Cannot read file", e);
        }

        return persistable.importFromString(output.toString());
    }
}
