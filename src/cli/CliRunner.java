package cli;

import domain.Task;
import domain.TaskManager;
import infrastructure.JsonTaskStore;

import java.util.Scanner;

public class CliRunner {

    private boolean running = true;
    private final Scanner keyboard = new Scanner(System.in);
    private final TaskManager taskManager;

    public CliRunner(TaskManager taskManager) {
        this.taskManager = taskManager;
    }

    public void run() {
        while (running) {
            System.out.print("task-cli ");
            String command = keyboard.nextLine().toLowerCase();
            handleCommand(command);
        }
    }

    private void handleCommand(String command) {
        switch (command) {
            case "add" -> createTaskFlow();
            case "list" -> listTasksFlow();
            case "exit" -> stop();
            default -> System.out.println("Invalid command");
        }
    }

    private void stop() {
        running = false;
        System.out.println("Thanks for using TaskTracker. <3 Developer");
    }

    private void createTaskFlow() {
        System.out.print("Description: ");
        String description = keyboard.nextLine();
        Task task = taskManager.create(description);
        System.out.printf("Task added successfully (ID: %d)\n", task.getId());
    }

    private void listTasksFlow() {
        taskManager.list()
                .forEach(System.out::println);
    }
}
