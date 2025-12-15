package cli;

import domain.Status;
import domain.Task;
import domain.TaskManager;

import java.util.List;
import java.util.Optional;
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
            case "list todo" -> listTasksFlow(Status.TODO);
            case "list in-progress" -> listTasksFlow(Status.IN_PROGRESS);
            case "list done" -> listTasksFlow(Status.DONE);
            case "delete" -> deleteTaskFlow();
            case "mark-in-progress" -> changeStatusFlow(Status.IN_PROGRESS);
            case "mark-done" -> changeStatusFlow(Status.DONE);
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
        System.out.printf("Task created (ID: %d)\n", task.getId());
    }

    private void listTasksFlow() {
        List<Task> list = taskManager.list();
        showTask(list);
    }

    private void listTasksFlow(Status status) {
        List<Task> list = taskManager.list(status);
        showTask(list);
    }

    private void showTask(List<Task> tasks) {
        if (tasks.isEmpty()) {
            System.out.println("no task found");
            return;
        }
        tasks.forEach(System.out::println);
    }

    private void deleteTaskFlow() {
        Optional<Long> longOptional = readId();

        if (longOptional.isEmpty()) {
            return;
        }
        boolean deleted = taskManager.delete(longOptional.get());

        String message = deleted ? String.format("task deleted (ID: %d)", longOptional.get()) : "task not found";
        System.out.println(message);
    }

    private void changeStatusFlow(Status status) {
        Optional<Long> longOptional = readId();

        if (longOptional.isEmpty()) {
            return;
        }
        Optional<Task> optionalTask = taskManager.changeStatus(longOptional.get(), status);

        String message = optionalTask.isPresent() ? "task updated" : "task not found";
        System.out.println(message);
    }

    private Optional<Long> readId() {
        while (true) {
            System.out.print("Enter ID: ");
            String input = keyboard.nextLine().toLowerCase();
            if (input.equals("cancel")) {
                return Optional.empty();
            }
            try {
                return Optional.of(Long.parseLong(input));
            } catch (NumberFormatException e) {
                System.out.println("Invalid ID (or type 'cancel'): ");
            }
        }
    }

    private void printHelp() {
        System.out.println("""
                Available commands:
                  add               - Create a new task
                  list              - List all tasks
                  list todo         - List tasks with status TODO
                  list in-progress  - List tasks with status IN_PROGRESS
                  list done         - List tasks with status DONE
                  mark-in-progress  - Mark a task as IN_PROGRESS
                  mark-done         - Mark a task as DONE
                  delete            - Delete a task
                  exit              - Exit the application
                  help              - Show this help message
                """);
    }
}
