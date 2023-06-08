package hwr.oop.group4.todo.persistence;

import hwr.oop.group4.todo.commons.exceptions.PersistenceRuntimeException;
import hwr.oop.group4.todo.persistence.configuration.FileAdapterConfiguration;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

class FileAdapterTest {

    @Test
    void save(@TempDir Path tempDir) {
        Persistable<PersistableTodoList> data = mock();
        when(data.exportAsString()).thenReturn("demo file content");

        Path path = Path.of(tempDir.toString() + "/FileAdapterTest.save.txt");
        File file = new File(path.toUri());
        FileAdapterConfiguration config = new FileAdapterConfiguration(file);

        SavePersistenceAdapter<PersistableTodoList> fileAdapter = new FileAdapter<>();
        fileAdapter.save(data, config);

        assertThat(Files.exists(path)).isTrue();
        assertThat(file).hasContent("demo file content");
    }

    @Test
    void saveWithIOException() throws IOException {
        Persistable<PersistableTodoList> data = mock();
        when(data.exportAsString()).thenReturn("demo file content");

        File file = mock();
        when(file.createNewFile()).thenThrow(IOException.class);
        FileAdapterConfiguration config = new FileAdapterConfiguration(file);

        SavePersistenceAdapter<PersistableTodoList> fileAdapter = new FileAdapter<>();

        assertThatThrownBy(() -> fileAdapter.save(data, config)).isInstanceOf(PersistenceRuntimeException.class);
    }

    @Test
    void saveWithEmptyFile() {
        Persistable<PersistableTodoList> data = mock();

        FileAdapterConfiguration config = new FileAdapterConfiguration(null);

        SavePersistenceAdapter<PersistableTodoList> fileAdapter = new FileAdapter<>();

        assertThatThrownBy(() -> fileAdapter.save(data, config)).isInstanceOf(PersistenceRuntimeException.class);
    }

    @Test
    void load(@TempDir Path tempDir) throws IOException {
        Persistable<PersistableTodoList> data = mock();

        Path path = Path.of(tempDir.toString() + "/FileAdapterTest.load.txt");
        File file = new File(path.toUri());
        file.createNewFile();
        FileWriter writer = new FileWriter(file);
        writer.write("demo file content");
        writer.close();

        LoadPersistenceAdapter<PersistableTodoList> fileAdapter = new FileAdapter<>();
        fileAdapter.load(data, new FileAdapterConfiguration(file));

        verify(data).importFromString("demo file content");
    }

    @Test
    void loadWithEmptyFile() {
        Persistable<PersistableTodoList> data = mock();

        FileAdapterConfiguration config = new FileAdapterConfiguration(null);

        LoadPersistenceAdapter<PersistableTodoList> fileAdapter = new FileAdapter<>();

        assertThatThrownBy(() -> fileAdapter.load(data, config)).isInstanceOf(PersistenceRuntimeException.class);
    }
}
