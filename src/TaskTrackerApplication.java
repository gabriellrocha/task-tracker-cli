public class TaskTrackerApplication {

    public static void main(String[] args) {

        System.out.println("Welcome the Task Tracker");

        TaskManager manager = new TaskManager(new IdGenerator());

        Task task = manager.create("new task");


        System.out.println(task.getId());
        System.out.println(task.getDescription());
        System.out.println(task.getStatus());
        System.out.println(task.getCreatedAt());
        System.out.println(task.getUpdatedAt());
    }
}
