// Receiver Thread - listens to queue and prints incoming messages
public class Receiver implements Runnable {

    private final String userName;        // this user's name
    private final MessageQueue incomingQueue; // messages sent TO this user
    private volatile boolean running = true;

    public Receiver(String userName, MessageQueue incomingQueue) {
        this.userName      = userName;
        this.incomingQueue = incomingQueue;
    }

    @Override
    public void run() {
        try {
            while (running) {
                // Blocks and waits until a message arrives
                String raw = incomingQueue.receiveMessage();

                // Check exit signal
                if (raw.startsWith("EXIT::")) {
                    String who = raw.replace("EXIT::", "");
                    System.out.println("\n  *** " + who + " has left the chat. ***");
                    System.out.println("  *** Type 'exit' to close your window. ***\n");
                    running = false;
                    break;
                }

                // Parse "SENDER::message"
                String[] parts = raw.split("::", 2);
                if (parts.length == 2) {
                    String sender  = parts[0];
                    String message = parts[1];

                    // Only display messages from the OTHER user
                    if (!sender.equals(userName)) {
                        System.out.println("\n  >> " + sender + ": " + message);
                        System.out.print("  You: ");
                    }
                }
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public void stop() {
        running = false;
    }
}
