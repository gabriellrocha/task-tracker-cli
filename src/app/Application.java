package app;

import cli.CliRunner;
import domain.TaskManager;
import infrastructure.IdGenerator;
import infrastructure.StorageEnvironment;

public final class Application {

    public void start() {
        StorageEnvironment storageEnvironment = new infrastructure.StorageEnvironment();
        IdGenerator idGenerator = new infrastructure.IdGenerator(storageEnvironment.idStateFile());
        TaskManager taskManager = new domain.TaskManager(idGenerator);
        CliRunner cliRunner = new CliRunner(taskManager);

        cliRunner.run();
    }

}
