package infrastructure;

import domain.Status;
import domain.Task;

import java.time.LocalDateTime;

public class TaskSerializer {

    private TaskSerializer() {
    }

    public static String toJson(Task task) {
        return String.format(
                "{\"id\":%d,\"description\":\"%s\",\"status\":\"%s\",\"createdAt\":\"%s\",\"updatedAt\":\"%s\"}",
                task.getId(),
                task.getDescription(),
                task.getStatus().name(),
                task.getCreatedAt(),
                task.getUpdatedAt()
        );
    }

    public static Task fromJson(String json) {
        Long id = Long.valueOf(extract(json, "id"));
        String description = extract(json, "description");
        Status status = Status.valueOf(extract(json, "status"));
        LocalDateTime createdAt = LocalDateTime.parse(extract(json, "createdAt"));
//        LocalDateTime updatedAt = LocalDateTime.parse(extract(json, "updatedAt"));
        String updatedAtValue = extract(json, "updatedAt");
        LocalDateTime updatedAt =
                updatedAtValue.equals("null")
                        ? null
                        : LocalDateTime.parse(updatedAtValue);

        return Task.restore(id, description, status, createdAt, updatedAt);
    }

    private static String extract(String json, String key) {
        String pattern = "\"" + key + "\":";
        int start = json.indexOf(pattern) + pattern.length();
        int end = json.indexOf(",", start);
        if (end == -1) {
            end = json.indexOf("}", start);
        }
        String value = json.substring(start, end).trim();
        return value.replace("\"", "");
    }
}
