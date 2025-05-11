import java.io.*;
import java.net.*;

public class TelnetHandler {
    public static void main(String[] args) {
        if (args.length != 1) {
            System.err.println("Usage: java TelnetHandler <port number>");
            return;
        }

        int portNumber = Integer.parseInt(args[0]);

        try (ServerSocket serverSocket = new ServerSocket(portNumber)) {
            System.out.println("Server started on port " + portNumber);
            while (true) {
                try {
                    Socket clientSocket = serverSocket.accept();
                    System.out.println("Client connected: " + clientSocket.getInetAddress());

                    new Thread(() -> handleClient(clientSocket)).start();

                } catch (IOException e) {
                    System.out.println("Error accepting client: " + e.getMessage());
                }
            }
        } catch (IOException e) {
            System.out.println("Could not start server: " + e.getMessage());
        }
    }

    private static void handleClient(Socket clientSocket) {
        try (
                InputStream input = clientSocket.getInputStream();
                OutputStream output = clientSocket.getOutputStream()
        ) {
            Game game = new Game(input, output);
            game.play();
        } catch (IOException e) {
            System.out.println("Error handling client: " + e.getMessage());
        }
    }
}
