package hwr.oop.group4.todo.persistence;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class FileAdapterTest {

    @Test
    void save(@TempDir Path tempDir) {
        Persisted data = mock();
        when(data.toString()).thenReturn("demo file content");

        Path path = Path.of(tempDir.toString() + "/FileAdapterTest.save.txt");
        File file = new File(path.toUri());

        FileAdapter fileAdapter = new FileAdapter(file);
        fileAdapter.save(data);

        assertThat(Files.exists(path)).isTrue();
        assertThat(file).hasContent("demo file content");
    }

    @Test
    void load() {
    }
}
