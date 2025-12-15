package infrastructure;

import domain.Task;

import java.io.IOException;
import java.nio.file.Files;

public class IdGenerator {

    private final StorageEnvironment storageEnvironment;
    private Long lastId;

    public IdGenerator(StorageEnvironment storageEnvironment) {
        this.storageEnvironment = storageEnvironment;
        this.lastId = load();
    }

    public Long nextId() {
        lastId++;
        persist(lastId);
        return lastId;
    }

    private Long load() {
        if (Files.exists(storageEnvironment.getTasksFilePath())) {
            return readLastTaskId();
        }
        return 0L;
    }

    private Long readLastTaskId() {
        try {
            return Files.readAllLines(storageEnvironment.getTasksFilePath())
                    .stream()
                    .filter(line -> !line.isBlank())
                    .map(TaskSerializer::fromJson)
                    .map(Task::getId)
                    .max(Long::compareTo)
                    .orElse(0L);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void persist(Long value) {
        try {
            Files.writeString(storageEnvironment.getTasksFilePath(), Long.toString(value));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
