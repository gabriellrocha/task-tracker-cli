package app;

import cli.CliRunner;
import domain.TaskManager;
import infrastructure.IdGenerator;
import infrastructure.JsonTaskStore;
import infrastructure.StorageEnvironment;

public final class Application {

    public void start() {
        StorageEnvironment storageEnvironment = new StorageEnvironment();
        IdGenerator idGenerator = new IdGenerator(storageEnvironment);
        JsonTaskStore jsonTaskStore = new JsonTaskStore(storageEnvironment.getTasksFilePath());
        TaskManager taskManager = new TaskManager(idGenerator, jsonTaskStore);
        CliRunner cliRunner = new CliRunner(taskManager);

        cliRunner.run();
    }
}
