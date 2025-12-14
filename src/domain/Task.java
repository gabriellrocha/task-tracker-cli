package domain;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class Task {

    private final Long id;
    private String description;
    private Status status;
    private final LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private Task(
            Long id,
            String description,
            Status status,
            LocalDateTime createdAt,
            LocalDateTime updatedAt
    ) {
        this.id = id;
        this.description = description;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public static Task create(Long id, String description) {
        return new Task(
                id,
                description,
                Status.TODO,
                LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS),
                null
        );
    }

    public static Task restore(
            Long id,
            String description,
            Status status,
            LocalDateTime createdAt,
            LocalDateTime updatedAt
    ) {
        return new Task(id, description, status, createdAt, updatedAt);
    }

    public Long getId() {
        return id;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", status=" + status +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
