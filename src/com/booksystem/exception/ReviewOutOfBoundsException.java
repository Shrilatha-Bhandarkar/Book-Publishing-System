package com.booksystem.exception;

/**
 * Exception thrown when a review rating is outside the allowed range.
 * Example: rating not between 1 and 5.
 */
public class ReviewOutOfBoundsException extends Exception {

    // Constructor to specify the invalid rating issue
    public ReviewOutOfBoundsException(String message) {
        super(message);
    }
}
