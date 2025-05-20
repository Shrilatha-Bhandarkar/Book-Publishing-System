package com.booksystem.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Represents a Review submitted by a user for a book.
 * Stores rating, comment, and timestamp.
 */
public class Review implements Serializable {
    private String reviewId;
    private User user; // User who submitted the review
    private int rating; // Rating given (e.g., 1 to 5)
    private String comment; // Optional comment with the review
    private LocalDateTime timestamp; // Timestamp of the review

    // Constructor to initialize review details and set the timestamp
    public Review(String reviewId, User user, int rating, String comment) {
        this.reviewId = reviewId;
        this.user = user;
        this.rating = rating;
        this.comment = comment;
        this.timestamp = LocalDateTime.now(); // Automatically set to current time
    }

    //Getters
    public String getReviewId() {
        return reviewId;
    }

    public User getUser() {
        return user;
    }

    public int getRating() {
        return rating;
    }

    public String getComment() {
        return comment;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }
}
