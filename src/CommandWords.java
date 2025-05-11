import java.io.PrintWriter;

public class CommandWords {
    private static final String[] validCommands = {
            "go", "quit", "help", "take", "inventory"
    };

    public CommandWords() {
        // nothing to do at the moment...
    }

    public boolean isCommand(String aString) {
        for (String command : validCommands) {
            if (command.equals(aString)) {
                return true;
            }
        }
        return false;
    }

    public void showAll(PrintWriter out) {
        for (String command : validCommands) {
            out.print(command + "  ");
        }
        out.println();
    }

    public void showAll() {
        for (String command : validCommands) {
            System.out.print(command + "  ");
        }
        System.out.println();
    }
}
