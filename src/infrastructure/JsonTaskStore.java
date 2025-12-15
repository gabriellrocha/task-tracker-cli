package infrastructure;

import domain.Status;
import domain.Task;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

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


    public boolean deleteById(Long id) {
        List<Task> tasks = findAll();

        int previousSize = tasks.size();

        List<Task> remaining = tasks.stream()
                .filter(task -> !task.getId().equals(id))
                .toList();

        if (previousSize == remaining.size()) {
            return false;
        }

        writeAll(remaining);
        return true;
    }

    public Optional<Task> changeStatus(Long id, Status status) {

        List<Task> tasks = findAll();

        Optional<Task> updated = tasks.stream()
                .filter(task -> task.getId().equals(id))
                .findFirst()
                .map(task -> {
                    task.setStatus(status);
                    task.setUpdatedAt(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));
                    return task;
                });

        if (updated.isPresent()) {
            writeAll(tasks);
        }
        return updated;
    }

    private void writeAll(List<Task> tasks) {

        try {
            Files.write(
                    path,
                    tasks.stream()
                            .map(TaskSerializer::toJson)
                            .map(json -> json + System.lineSeparator())
                            .toList(),
                    StandardOpenOption.CREATE,
                    StandardOpenOption.TRUNCATE_EXISTING
            );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
