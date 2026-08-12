package com.campussync;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Module1 {

    static class Room {
        String id;
        boolean isOccupied;

        Room(String id, boolean isOccupied) {
            this.id = id;
            this.isOccupied = isOccupied;
        }
    }

 
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public static boolean shiftRoom(Map<String, Room> rooms, String currentRoomId, String targetRoomId) {
        Room currentRoom = rooms.get(currentRoomId);
        Room targetRoom = rooms.get(targetRoomId);

      
        if (targetRoom == null) {
            System.out.println("\nError: Target room '" + targetRoomId + "' does not exist.");
            return false;
        }
        if (targetRoom.isOccupied) {
            System.out.println("\nShift Failed: Target room '" + targetRoomId + "' is already occupied.");
            return false;
        }

        targetRoom.isOccupied = true;

    
        if (currentRoom != null && currentRoom.isOccupied) {
            currentRoom.isOccupied = false;
            System.out.println("\nAuto-released original room: " + currentRoomId);
        }

        System.out.println("Successfully shifted class to room: " + targetRoomId);
        return true;
    }

    public static void printRoomStatusAsJson(Map<String, Room> rooms) {
        System.out.println("\n--- Current System State (JSON Export via Gson Dependency) ---");
        String jsonOutput = gson.toJson(rooms);
        System.out.println(jsonOutput);
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Map<String, Room> rooms = new HashMap<>();

        rooms.put("LH-101", new Room("LH-101", true));   
        rooms.put("LH-102", new Room("LH-102", true));   
        rooms.put("AUDI-01", new Room("AUDI-01", false)); 
        rooms.put("LAB-03", new Room("LAB-03", false));    

        boolean running = true;

        
        System.out.println("  Welcome to CampusSync Shift Module  ");
    

        while (running) {
            System.out.println("\nSelect an Option:");
            System.out.println("1. View All Rooms (JSON Output)");
            System.out.println("2. Request Room Shift");
            System.out.println("3. Exit");
            System.out.print("Enter choice (1-3): ");

            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1":
                    printRoomStatusAsJson(rooms);
                    break;

                case "2":
                    printRoomStatusAsJson(rooms);
                    System.out.print("\nEnter your Current (Default) Room ID: ");
                    String currentRoom = scanner.nextLine().trim().toUpperCase();

                    System.out.print("Enter Target Room ID to Shift into: ");
                    String targetRoom = scanner.nextLine().trim().toUpperCase();

                    shiftRoom(rooms, currentRoom, targetRoom);
                    break;

                case "3":
                    System.out.println("\nExiting CampusSync. Goodbye!");
                    running = false;
                    break;

                default:
                    System.out.println("\nInvalid choice! Please enter 1, 2, or 3.");
            }
        }

        scanner.close();
    }
}