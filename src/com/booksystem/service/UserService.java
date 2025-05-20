package com.booksystem.service;

import java.io.Serializable;
import com.booksystem.entity.User;
import com.booksystem.exception.InvalidInputException;
import com.booksystem.util.InputValidator;

import java.util.HashMap;
import java.util.Map;

/**
 * Service class that manages operations related to Users. Provides functionality
 * for adding new users with validation, checking duplicates, and retrieving users
 * by ID or fetching all users.
 */
public class UserService implements Serializable {
    private static final long serialVersionUID = 1L;

    private Map<String, User> userMap = new HashMap<>();

    // Adds a new user after validating username and email.
    // Throws InvalidInputException if username or email is invalid or already exists.
    public void addUser(String userId, String username, String email) throws InvalidInputException {
        InputValidator.validateName(username, "Username");
        InputValidator.validateEmail(email);

        // Check for duplicate username (case-insensitive)
        boolean usernameExists = userMap.values().stream()
            .anyMatch(u -> u.getUsername().equalsIgnoreCase(username));
        if (usernameExists) {
            throw new InvalidInputException("Username '" + username + "' already exists.");
        }

        // Check for duplicate email (case-insensitive)
        boolean emailExists = userMap.values().stream()
            .anyMatch(u -> u.getEmail().equalsIgnoreCase(email));
        if (emailExists) {
            throw new InvalidInputException("Email '" + email + "' already registered.");
        }

        User user = new User(userId, username, email);
        userMap.put(userId, user);
        System.out.println("User added: " + username);
    }

    // Returns the User object for the given userId or null if not found.
    public User getUserById(String userId) {
        return userMap.get(userId);
    }

    // Returns the map of all users.
    public Map<String, User> getAllUsers() {
        return userMap;
    }
}
