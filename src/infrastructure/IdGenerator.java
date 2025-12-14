package infrastructure;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class IdGenerator {

    private final Path path;
    private Long lastId;

    public IdGenerator(Path path) {
        this.path = path;
        this.lastId = load();
    }

    public Long nextId() {
        lastId++;
        persist(lastId);
        return lastId;
    }


    private Long load() {
        if (!Files.exists(path)) {
            return 0L;
        }
        try {
            return Long.parseLong(Files.readString(path).trim());
        } catch (IOException e) {
            // TODO: My Exception
            throw new RuntimeException(e);
        }
    }

    private void persist(Long value) {
        try {
            Files.writeString(path, Long.toString(value));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
