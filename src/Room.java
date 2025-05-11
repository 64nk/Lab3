import feature.Item;

import java.util.Set;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Class Room - a room in an adventure game.
 *
 * This class is part of the "World of Zuul" application.
 * 
 * A "Room" represents one location in the scenery of the game. It is connected to other rooms via exits.
 * Each room can contain items that players can pick up.
 */
public class Room {
    private String description;
    private HashMap<String, Room> exits;        
    private ArrayList<Item> items;               // NEW: Items in the room

    public Room(String description) {
        this.description = description;
        exits = new HashMap<>();
        items = new ArrayList<>();
    }

    public void setExit(String direction, Room neighbor) {
        exits.put(direction, neighbor);
    }

    public String getShortDescription() {
        return description;
    }

    public String getLongDescription() {
        return "You are " + description + ".\n" + getExitString() + "\n" + getItemString();
    }

    private String getExitString() {
        String returnString = "Exits:";
        for (String exit : exits.keySet()) {
            returnString += " " + exit;
        }
        return returnString;
    }

    public Room getExit(String direction) {
        return exits.get(direction);
    }

    // NEW: Add an item to the room
    public void addItem(Item item) {
        items.add(item);
    }

    // NEW: Take (remove) an item by name
    public Item takeItem(String itemName) {
        for (Item item : items) {
            if (item.getName().equalsIgnoreCase(itemName)) {
                items.remove(item);
                return item;
            }
        }
        return null;
    }

    // NEW: Display items in the room
    private String getItemString() {
        if (items.isEmpty()) {
            return "There are no items here.";
        }
        StringBuilder sb = new StringBuilder("Items here:");
        for (Item item : items) {
            sb.append("\n- ").append(item.getName()).append(": ").append(item.getDescription());
        }
        return sb.toString();
    }
}
