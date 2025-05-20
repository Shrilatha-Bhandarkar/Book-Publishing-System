package com.booksystem.exception;

/**
 * Exception thrown when user input is invalid or does not meet system requirements.
 * Common examples: empty fields, invalid formats, null values.
 */
public class InvalidInputException extends Exception {

    // Constructor to pass a meaningful validation message
    public InvalidInputException(String message) {
        super(message);
    }
}
