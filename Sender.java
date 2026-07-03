import java.util.Scanner;

// Sender Thread - reads user input and puts it into the queue
public class Sender implements Runnable {

    private final String userName;
    private final MessageQueue outgoingQueue; // messages this user sends
    private final Scanner scanner;

    public Sender(String userName, MessageQueue outgoingQueue, Scanner scanner) {
        this.userName     = userName;
        this.outgoingQueue = outgoingQueue;
        this.scanner      = scanner;
    }

    @Override
    public void run() {
        try {
            while (true) {
                // Read input from console
                String input = scanner.nextLine();

                // Exit command
                if (input.equalsIgnoreCase("exit")) {
                    outgoingQueue.sendMessage("EXIT::" + userName);
                    System.out.println("[" + userName + "] You left the chat.");
                    break;
                }

                if (!input.trim().isEmpty()) {
                    // Format: "USERNAME::message"
                    outgoingQueue.sendMessage(userName + "::" + input);
                }
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
