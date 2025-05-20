package com.booksystem.entity;

import java.io.Serializable;

/**
 * Base class representing a system user.
 * Can be extended by Author, Admin, Reader, etc.
 */
public class User implements Serializable {
    private String userId;
    private String username;
    private String email;

    // Constructor to initialize user details
    public User(String userId, String username, String email) {
        this.userId = userId;
        this.username = username;
        this.email = email;
    }

    //Getters
    public String getUserId() {
        return userId;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }
}
