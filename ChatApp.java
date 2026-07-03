import java.util.Scanner;

/*
 * ============================================================
 *  SIMPLE CONSOLE CHAT APP  —  Two-User Simulation
 * ============================================================
 *
 *  HOW IT WORKS:
 *
 *   User1 (Alice)                    User2 (Bob)
 *       |                                |
 *   [Sender Thread]  --> queueAtoB --> [Receiver Thread]
 *   [Receiver Thread] <-- queueBtoA <-- [Sender Thread]
 *
 *  Two BlockingQueues act as message channels.
 *  Each user has one Sender thread (types & sends)
 *  and one Receiver thread (listens & displays).
 *
 *  Type 'exit' to leave the chat.
 * ============================================================
 */
public class ChatApp {

    public static void main(String[] args) throws InterruptedException {

        Scanner scanner = new Scanner(System.in);

        // --- Welcome Banner ---
        System.out.println("╔══════════════════════════════════════╗");
        System.out.println("║      JAVA CONSOLE CHAT APP           ║");
        System.out.println("║   Two-User Thread Simulation         ║");
        System.out.println("╚══════════════════════════════════════╝");
        System.out.println();

        // --- Get User Names ---
        System.out.print("Enter name for User 1: ");
        String name1 = scanner.nextLine().trim();
        if (name1.isEmpty()) name1 = "Alice";

        System.out.print("Enter name for User 2: ");
        String name2 = scanner.nextLine().trim();
        if (name2.isEmpty()) name2 = "Bob";

        System.out.println();
        System.out.println("══════════════════════════════════════");
        System.out.println("  Chat started between " + name1 + " and " + name2);
        System.out.println("  Type 'exit' to leave the chat.");
        System.out.println("══════════════════════════════════════");
        System.out.println("  [" + name1 + " goes first — type below]");
        System.out.println();
        System.out.print("  You: ");

        // --- Create Two Shared Queues ---
        // queueAtoB: Alice sends → Bob receives
        // queueBtoA: Bob sends → Alice receives
        MessageQueue queueAtoB = new MessageQueue();
        MessageQueue queueBtoA = new MessageQueue();

        // --- Create Two Users ---
        // User1: sends to queueAtoB, receives from queueBtoA
        // User2: sends to queueBtoA, receives from queueAtoB
        User user1 = new User(name1, queueAtoB, queueBtoA, scanner);
        User user2 = new User(name2, queueBtoA, queueAtoB, scanner);

        // --- Start Both Users ---
        user1.startChat();
        user2.startChat();

        // --- Wait for both users to exit ---
        user1.waitUntilDone();
        user2.waitUntilDone();

        // --- Cleanup ---
        user1.stopReceiver();
        user2.stopReceiver();
        scanner.close();

        System.out.println();
        System.out.println("══════════════════════════════════════");
        System.out.println("  Chat ended. Goodbye!");
        System.out.println("══════════════════════════════════════");
    }
}
