package hwr.oop.group4.todo.persistence.configuration;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;

class FileAdapterConfigurationTest {

    @Test
    void getFile() {
        final FileAdapterConfiguration config = new FileAdapterConfiguration(new File("1234.xyz"));

        Assertions.assertThat(config.getFile()).hasValue(new File("1234.xyz"));
    }

    @Test
    void getFileEmpty() {
        final FileAdapterConfiguration config = new FileAdapterConfiguration(null);

        Assertions.assertThat(config.getFile()).isEmpty();
    }

    @Test
    void setFile() {
        final FileAdapterConfiguration config = new FileAdapterConfiguration(null);
        config.setFile(new File("1234.xyz"));

        Assertions.assertThat(config.getFile()).hasValue(new File("1234.xyz"));
    }

}