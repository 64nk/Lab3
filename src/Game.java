import feature.Item;
import feature.Inventory;
import java.io.*;

public class Game {
    private final Parser parser;
    private Room currentRoom;
    private final Inventory inventory;
    private BufferedReader in;
    private PrintWriter out;

    public static void main(String[] args) {
        Game game = new Game(System.in, System.out);
        game.play();
    }

    public Game(InputStream input, OutputStream output) {
        this.in = new BufferedReader(new InputStreamReader(input));
        this.out = new PrintWriter(output, true);
        this.parser = new Parser(in, out);
        this.inventory = new Inventory();
        createRooms();
    }

    private void createRooms() {
        Room outside, theater, pub, lab, office, canteen;

        // create the rooms
        outside = new Room("outside the main entrance of the university");
        theater = new Room("in a lecture theater");
        pub = new Room("in the campus pub");
        lab = new Room("in a computing lab");
        office = new Room("in the computing admin office");
        canteen = new Room("in a cozy university canteen with food aroma in the air");
        
        // initialise room exits
        outside.setExit("east", theater);
        outside.setExit("south", lab);
        outside.setExit("west", pub);

        theater.setExit("west", outside);
        pub.setExit("east", outside);
        lab.setExit("north", outside);
        lab.setExit("east", office);
        office.setExit("west", lab);
        office.setExit("north", canteen);
        canteen.setExit("west", office);

        // add items to rooms
        theater.addItem(new Item("Key", "An useless key"));
        pub.addItem(new Item("Beer", "A cold bottle of beer"));
        lab.addItem(new Item("Notebook", "A student's notes on Java"));

        Item saschaCard = new Item("saschaCard", "a university staff card labeled 'Sascha' — this might get you some food. Tip: take saschaCard");
        office.addItem(saschaCard);
        
        currentRoom = outside;  // start game outside
    }

    public void play() {
        printWelcome();

        boolean finished = false;
        while (!finished) {
            Command command = parser.getCommand();
            finished = processCommand(command);
        }
        System.out.println("Thank you for playing. Good bye.");
    }

    private void printWelcome() {
        System.out.println();
        System.out.println("Welcome to the World of Zuul!");
        System.out.println("You are now in front of your university");
        System.out.println("but before you can do anything, you are really starving.");
        System.out.println("Luckily there is a canteen so you know what to do... don't you?");
        System.out.println("Type 'help' if you need help.");
        System.out.println();
        System.out.println(currentRoom.getLongDescription());
    }

    private boolean processCommand(Command command) {
        String commandWord = command.getCommandWord();

        if (commandWord == null) {
            System.out.println("I don't know what you mean...");
            return false;
        }

        switch (commandWord) {
            case "help":
                printHelp();
                break;
            case "go":
                goRoom(command);
                break;
            case "quit":
                return true;
            case "take":
                takeItem(command);
                break;
            case "inventory":
                inventory.showInventory();
                break;
            default:
                System.out.println("I don't know what you mean...");
                break;
        }
        return false;
    }

    private void printHelp() {
        System.out.println("You are lost. You are alone. You wander");
        System.out.println("around at the university.");
        System.out.println();
        System.out.println("Your command words are:");
        parser.showCommands();
    }

    private void goRoom(Command command) {
        if (!command.hasSecondWord()) {
            System.out.println("Go where?");
            return;
        }

    String direction = command.getSecondWord();
    Room nextRoom = currentRoom.getExit(direction);

        if (nextRoom == null) {
            System.out.println("There is no door!");
        } else {
        currentRoom = nextRoom;

        // Combo win logic
        if (nextRoom.getShortDescription().contains("canteen")) {
            if (inventory.hasItem("saschaCard")) {
                System.out.println("You step into the canteen, tray in hand, eyes scanning the buffet.");
                System.out.println("There’s rice, pasta, steaming curry, crispy fries... you pile on a bit of everything.");
                System.out.println("At the end of the line, you hand Sascha's card to the cashier.");
                System.out.println("They swipe it without a word and nod you through.");
                System.out.println("You find a seat by the window, the first bite tastes like victory.");
                System.out.println("You win!");

                return;
            } else {
                System.out.println("The canteen is locked. You need Sascha's card.");
                return;
            }
        }

        System.out.println(currentRoom.getLongDescription());
        }
    }


    private void takeItem(Command command) {
        if (!command.hasSecondWord()) {
            System.out.println("Take what?");
            return;
        }

        String itemName = command.getSecondWord();
        Item item = currentRoom.takeItem(itemName);
        if (item != null) {
            inventory.addItem(item);
            System.out.println("You picked up the " + item.getName() + ".");
        } else {
            System.out.println("That item is not here.");
        }
    }
}