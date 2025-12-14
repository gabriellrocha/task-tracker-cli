package cli;

import domain.Status;
import domain.Task;
import domain.TaskManager;

import java.util.List;
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
            case "list todo" -> listTasksByStatusFlow(Status.TODO);
            case "list in-progress" -> listTasksByStatusFlow(Status.IN_PROGRESS);
            case "list done" -> listTasksByStatusFlow(Status.DONE);
            case "help" -> printHelp();
            case "exit" -> stop();
            default -> System.out.println("Invalid command. Type 'help' to see available commands.");
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
        List<Task> list = taskManager.list();
        if (list.isEmpty()) {
            System.out.println("No tasks found");
            return;
        }

        list.forEach(System.out::println);
    }

    private void listTasksByStatusFlow(Status status) {
        List<Task> list = taskManager.list(status);
        if (list.isEmpty()) {
            System.out.println("No tasks found");
            return;
        }

        list.forEach(System.out::println);
    }

    private void printHelp() {
        System.out.println("""
                Available commands:
                  add               - Create a new task
                  list              - List all tasks
                  list todo         - List tasks with status TODO
                  list in-progress  - List tasks with status IN_PROGRESS
                  list done         - List tasks with status DONE
                  exit              - Exit the application
                  help              - Show this help message
                """);
    }
}
