package infrastructure;

import java.nio.file.Path;

public final class StorageEnvironment {

    private final Path baseDir;

    public StorageEnvironment() {
        this.baseDir = initBaseDir();
    }

    public Path getTasksFilePath() {
        return baseDir.resolve("tasks.jsonl");
    }

    private Path initBaseDir() {
        return Path.of(System.getProperty("user.home"), ".tasktracker");
    }
}
