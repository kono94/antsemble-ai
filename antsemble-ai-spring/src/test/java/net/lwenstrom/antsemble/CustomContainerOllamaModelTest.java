package net.lwenstrom.antsemble;

import org.junit.jupiter.api.Test;
import org.testcontainers.containers.GenericContainer;

public class CustomContainerOllamaModelTest {

    @Test
    void ollamaModelTest() {
        try (GenericContainer<?> ollama = new GenericContainer<>("ollama/ollama:0.10.0").withExposedPorts(11434)) {
            ollama.start();

            String url = "http://" + ollama.getHost() + ":" + ollama.getFirstMappedPort();
        }
    }
}
