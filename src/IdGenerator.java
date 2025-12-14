import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class IdGenerator {

    private final Path stateFile;
    private Long lastId;

    public IdGenerator() {
        this.stateFile = createStateFile();
        this.lastId = load();
    }

    public Long nextId() {
        lastId++;
        persist(lastId);
        return lastId;
    }

    private Path createStateFile() {
        Path baseDir = Path.of(System.getProperty("user.home"), ".tasktracker");
        try {
            Files.createDirectories(baseDir);
        } catch (IOException e) {
            // TODO: My Exception
            throw new RuntimeException(e);
        }
        return baseDir.resolve("id.state");
    }

    private Long load() {
        if (!Files.exists(stateFile)) {
            return 0L;
        }
        try {
            return Long.parseLong(Files.readString(stateFile).trim());
        } catch (IOException e) {
            // TODO: My Exception
            throw new RuntimeException(e);
        }
    }

    private void persist(Long value) {
        try {
            Files.writeString(stateFile, Long.toString(value));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
