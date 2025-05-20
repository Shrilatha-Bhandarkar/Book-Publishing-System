package com.booksystem.exception;

/**
 * Exception thrown when attempting to add a book
 * that already exists (e.g., duplicate title or ID).
 */
public class DuplicateBookException extends Exception {
    
    // Constructor to pass custom error message to the base Exception class
    public DuplicateBookException(String message) {
        super(message);
    }
}
