package domain;

import infrastructure.IdGenerator;
import infrastructure.JsonTaskStore;

import java.util.List;
import java.util.Optional;

public class TaskManager {

    private final IdGenerator idGenerator;
    private final JsonTaskStore jsonTaskStore;

    public TaskManager(IdGenerator idGenerator, JsonTaskStore jsonTaskStore) {
        this.idGenerator = idGenerator;
        this.jsonTaskStore = jsonTaskStore;
    }

    public Task create(String description) {
        Task task = Task.create(idGenerator.nextId(), description);
        jsonTaskStore.save(task);
        return task;
    }

    public List<Task> list() {
        return jsonTaskStore.findAll();
    }

    public List<Task> list(Status status) {
        return list().stream()
                .filter(task -> task.getStatus().equals(status))
                .toList();
    }

    public boolean delete(Long id) {
        return jsonTaskStore.deleteById(id);
    }

    public Optional<Task> changeStatus(Long id, Status status) {
        return jsonTaskStore.changeStatus(id, status);
    }
}
