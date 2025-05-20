package com.booksystem.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a Book in the system.
 * Implements Serializable for object persistence.
 */
public class Book implements Serializable {
    private String bookId;
    private String title;
    private String content;
    private Author author; // Reference to the author of the book
    private List<Review> reviews; // List of reviews for this book

    // Constructor to initialize book attributes
    public Book(String bookId, String title, String content, Author author) {
        this.bookId = bookId;
        this.title = title;
        this.content = content;
        this.author = author;
        this.reviews = new ArrayList<>();
    }


    // Adds a review to the book
    public void addReview(Review review) {
        reviews.add(review);
    }

    // Calculates and returns the average rating of the book
    public double getAverageRating() {
        if (reviews.isEmpty()) return 0.0;
        return reviews.stream().mapToInt(Review::getRating).average().orElse(0.0);
    }
    
    //getter
    public String getBookId() {
        return bookId;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public Author getAuthor() {
        return author;
    }

    public List<Review> getReviews() {
        return reviews;
    }
}
