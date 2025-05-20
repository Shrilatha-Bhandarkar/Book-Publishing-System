package com.booksystem.util;

import java.io.*;

/**
 * Utility class for saving and loading serialized application state data
 * to and from files. Provides methods to persist any serializable object
 * and to retrieve it later, handling exceptions gracefully.
 */
public class DataStore {

    // Saves the given serializable data object to the specified filename
    public static void saveState(Object data, String filename) {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(filename))) {
            out.writeObject(data);
            System.out.println("Data saved to " + filename);
        } catch (IOException e) {
            System.out.println("Error saving data: " + e.getMessage());
        }
    }

    // Loads and returns the serialized object from the given filename, or null if not found
    public static Object loadState(String filename) {
        File file = new File(filename);
        if (!file.exists()) {
            System.out.println("No saved state found. Starting fresh.");
            return null;
        }
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(filename))) {
            Object data = in.readObject();
            System.out.println("Data loaded from " + filename);
            return data;
        } catch (IOException | ClassNotFoundException e) {
            System.out.println(" Error loading data: " + e.getMessage());
            return null;
        }
    }
}
