import java.util.ArrayList;
import java.util.Scanner;

public class ToDoList {

    // ArrayList to store tasks
    static ArrayList<String> tasks = new ArrayList<>();
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("=============================");
        System.out.println("      JAVA TO-DO LIST APP    ");
        System.out.println("=============================");

        boolean running = true;

        while (running) {
            showMenu();
            int choice = getChoice();

            switch (choice) {
                case 1:
                    addTask();
                    break;
                case 2:
                    removeTask();
                    break;
                case 3:
                    displayTasks();
                    break;
                case 4:
                    System.out.println("\nGoodbye! Stay productive!");
                    running = false;
                    break;
                default:
                    System.out.println("\nInvalid choice! Please enter 1-4.");
            }
        }

        scanner.close();
    }

    // Show the menu options
    static void showMenu() {
        System.out.println("\n-----------------------------");
        System.out.println("  1. Add Task");
        System.out.println("  2. Remove Task");
        System.out.println("  3. Display All Tasks");
        System.out.println("  4. Exit");
        System.out.println("-----------------------------");
        System.out.print("Enter your choice: ");
    }

    // Get user choice safely
    static int getChoice() {
        int choice = -1;
        try {
            choice = Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            // invalid input handled in switch default
        }
        return choice;
    }

    // Add a new task
    static void addTask() {
        System.out.print("\nEnter task name: ");
        String task = scanner.nextLine().trim();

        if (task.isEmpty()) {
            System.out.println("Task cannot be empty!");
        } else {
            tasks.add(task);
            System.out.println("Task added: \"" + task + "\"");
        }
    }

    // Remove a task by number
    static void removeTask() {
        if (tasks.isEmpty()) {
            System.out.println("\nNo tasks to remove!");
            return;
        }

        displayTasks();
        System.out.print("\nEnter task number to remove: ");

        int num = -1;
        try {
            num = Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("Invalid number!");
            return;
        }

        if (num < 1 || num > tasks.size()) {
            System.out.println("Invalid task number! Please enter between 1 and " + tasks.size());
        } else {
            String removed = tasks.remove(num - 1);
            System.out.println("Task removed: \"" + removed + "\"");
        }
    }

    // Display all tasks
    static void displayTasks() {
        System.out.println("\n--- YOUR TASKS ---");
        if (tasks.isEmpty()) {
            System.out.println("No tasks yet! Add some tasks.");
        } else {
            for (int i = 0; i < tasks.size(); i++) {
                System.out.println((i + 1) + ". " + tasks.get(i));
            }
            System.out.println("Total tasks: " + tasks.size());
        }
    }
}
