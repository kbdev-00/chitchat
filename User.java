import java.util.Scanner;

// User - bundles a name, sender thread, and receiver thread together
public class User {

    private final String name;
    private final Thread senderThread;
    private final Thread receiverThread;
    private final Receiver receiver;

    public User(String name, MessageQueue outgoing, MessageQueue incoming, Scanner scanner) {
        this.name = name;

        // Create Sender and Receiver
        Sender sender = new Sender(name, outgoing, scanner);
        this.receiver = new Receiver(name, incoming);

        // Wrap in threads
        this.senderThread   = new Thread(sender,   name + "-Sender");
        this.receiverThread = new Thread(receiver, name + "-Receiver");

        // Mark as daemon so JVM exits when main thread ends
        this.senderThread.setDaemon(false);
        this.receiverThread.setDaemon(true);
    }

    // Start both threads
    public void startChat() {
        receiverThread.start();
        senderThread.start();
    }

    // Wait for sender thread to finish (user typed 'exit')
    public void waitUntilDone() throws InterruptedException {
        senderThread.join();
    }

    public String getName() {
        return name;
    }

    public void stopReceiver() {
        receiver.stop();
        receiverThread.interrupt();
    }
}
