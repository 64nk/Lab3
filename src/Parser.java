import java.io.*;

public class Parser {
    private BufferedReader in;
    private PrintWriter out;
    private CommandWords commands;

    public Parser(BufferedReader in, PrintWriter out) {
        this.in = in;
        this.out = out;
        commands = new CommandWords();
    }

    public Command getCommand() {
        try {
            out.print("> ");
            out.flush(); // Flush to ensure prompt is sent (new stuff for me)

            String inputLine = in.readLine();
            if (inputLine == null) {
                return new Command("quit", null); // gracefully end if client disconnects
            }

            String word1 = null;
            String word2 = null;

            String[] words = inputLine.trim().split("\\s+");
            if (words.length > 0) {
                word1 = words[0];
            }
            if (words.length > 1) {
                word2 = words[1];
            }

            if (commands.isCommand(word1)) {
                return new Command(word1, word2);
            } else {
                return new Command(null, word2);
            }

        } catch (IOException e) {
            out.println("Error reading command.");
            return new Command(null, null);
        }
    }

    public void showCommands() {
        commands.showAll(out);
    }
}
