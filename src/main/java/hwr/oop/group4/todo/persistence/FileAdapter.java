package hwr.oop.group4.todo.persistence;

import hwr.oop.group4.todo.commons.exceptions.PersistenceRuntimeException;
import hwr.oop.group4.todo.persistence.configuration.Configuration;
import hwr.oop.group4.todo.persistence.configuration.FileAdapterConfiguration;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class FileAdapter implements LoadPersistenceAdapter, SavePersistenceAdapter {

    private FileAdapterConfiguration getFileAdapterConfiguration(Configuration config) {
        if (config instanceof FileAdapterConfiguration) {
            return (FileAdapterConfiguration) config;
        }
        throw new PersistenceRuntimeException("Wrong config provided, should be FileAdapterConfiguration");
    }

    @Override
    public void save(Persistable data, Configuration config) {
        File file = getFileAdapterConfiguration(config).getFile()
                .orElseThrow(() -> new PersistenceRuntimeException("No file provided"));
        try {
            file.createNewFile();
        } catch (IOException e) {
            throw new PersistenceRuntimeException("Cannot create file", e);
        }

        try (FileWriter writer = new FileWriter(file)) {
            writer.write(data.exportAsString());
        } catch (IOException e) {
            throw new PersistenceRuntimeException("Cannot write file", e);
        }
    }

    @Override
    public Persistable load(Persistable data, Configuration config) {
        File file = getFileAdapterConfiguration(config).getFile()
                .orElseThrow(() -> new PersistenceRuntimeException("No file provided"));
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
