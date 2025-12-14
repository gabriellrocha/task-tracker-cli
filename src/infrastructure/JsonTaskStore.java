package infrastructure;

import domain.Task;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.List;

public class JsonTaskStore {

    private final Path path;

    public JsonTaskStore(Path path) {
        this.path = path;
    }

    public void save(Task task) {
        String json = TaskSerializer.toJson(task) + System.lineSeparator();
        try {
            Files.writeString(
                    path,
                    json,
                    StandardOpenOption.CREATE,
                    StandardOpenOption.APPEND
            );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Task> findAll() {
        if (Files.notExists(path)) {
            return List.of();
        }
        try {
            return Files.readAllLines(path)
                    .stream()
                    .filter(line -> !line.isBlank())
                    .map(TaskSerializer::fromJson)
                    .toList();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
