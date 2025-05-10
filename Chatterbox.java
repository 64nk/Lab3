import java.net.*;
import java.io.*;

public class Chatterbox {
    public static void main(String[] args) {

        if (args.length != 1) {
            System.err.println("Usage: java Chatterbox <port number>");
            return;
        }

        int portNumber = Integer.parseInt(args[0]);

        try (
                ServerSocket serverSocket =
                        new ServerSocket(Integer.parseInt(args[0]));
                Socket clientSocket = serverSocket.accept();
                PrintWriter out =
                        new PrintWriter(clientSocket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(clientSocket.getInputStream()));
                BufferedReader serverInput =
                        new BufferedReader(new InputStreamReader(System.in))
        ) {
            Thread receive = new Thread(() -> {
                String msg;
                try {
                    while ((msg = in.readLine()) != null) {
                        System.out.println("Client: " + msg);
                    }
                } catch (IOException e) {
                    System.out.println("Client disconnected.");
                }
            });

            Thread send = new Thread(() -> {
                String msg;
                try {
                    while ((msg = serverInput.readLine()) != null) {
                        out.println(msg);
                    }
                } catch (IOException e) {
                    System.out.println("Error sending message.");
                }
            });

            receive.start();
            send.start();

            receive.join();
            send.join();

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }

            /*String inputLine;
            while ((inputLine = in.readLine()) != null) {
                out.println(inputLine);
            }
        } catch (IOException e) {
            System.out.println("Exception caught when trying to listen on port "
                    + portNumber + " or listening for a connection");
            System.out.println(e.getMessage());
        }*/
    }
}
