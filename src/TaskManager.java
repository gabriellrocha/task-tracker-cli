public class TaskManager {

    private final IdGenerator idGenerator;

    public TaskManager(IdGenerator idGenerator) {
        this.idGenerator = idGenerator;
    }

    public Task create(String description) {
        return new Task(idGenerator.nextId(), description);
    }
}
