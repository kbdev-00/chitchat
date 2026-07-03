import java.util.concurrent.LinkedBlockingQueue;

// Shared thread-safe queue between two users
public class MessageQueue {

    private final LinkedBlockingQueue<String> queue = new LinkedBlockingQueue<>();
    
    // Put a message into the queue (sender calls this)
    public void sendMessage(String message) throws InterruptedException {
        queue.put(message);
    }

    // Take a message from the queue (receiver calls this) - WAITS if empty
    public String receiveMessage() throws InterruptedException {
        return queue.take();
    }

    // Check if there's a message available (non-blocking)
    public boolean hasMessage() {
        return !queue.isEmpty();
    }
}
