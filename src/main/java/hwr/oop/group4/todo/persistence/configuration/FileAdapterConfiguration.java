package hwr.oop.group4.todo.persistence.configuration;

import java.io.File;
import java.util.Optional;

public class FileAdapterConfiguration implements Configuration {

    private File file;

    public FileAdapterConfiguration(File file) {
        this.file = file;
    }

    public Optional<File> getFile() {
        return Optional.ofNullable(file);
    }

    public void setFile(File file) {
        this.file = file;
    }
}
