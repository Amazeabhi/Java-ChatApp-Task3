import java.io.*;
import java.net.*;
import java.util.Scanner;

public class ChatClient {
    public static void main(String[] args) {
        String host = "localhost";
        int port = 1234;

        try (Socket socket = new Socket(host, port)) {
            System.out.println("✅ Connected to the chat server!");

            // Reader to get messages from server
            BufferedReader serverReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            // Writer to send messages to server
            PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);

            // Thread to read messages from server
            new Thread(() -> {
                try {
                    String serverMsg;
                    while ((serverMsg = serverReader.readLine()) != null) {
                        System.out.println("📨 " + serverMsg);
                    }
                } catch (IOException e) {
                    System.out.println("❌ Server closed the connection.");
                }
            }).start();

            // Main thread to take user input and send to server
            Scanner scanner = new Scanner(System.in);
            while (true) {
                String msg = scanner.nextLine();
                writer.println(msg);
            }

        } catch (IOException e) {
            System.out.println("❌ Connection error: " + e.getMessage());
        }
    }
}
