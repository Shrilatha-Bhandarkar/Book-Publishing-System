package com.booksystem.service;

import com.booksystem.entity.Author;
import com.booksystem.entity.Book;
import com.booksystem.entity.Review;
import com.booksystem.entity.User;
import com.booksystem.exception.ReviewOutOfBoundsException;
import com.booksystem.thread.NotificationThread;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


/**
 * Service class that manages operations related to Reviews. Provides
 * functionality for adding reviews to books, validating ratings,
 * retrieving reviews in reverse chronological order, and notifying authors
 * asynchronously about new reviews.
 */
public class ReviewService implements Serializable {
    private static final long serialVersionUID = 1L;

    private static int reviewCounter = 1;

    // Adds a new review to a book after validating the rating.
    public void addReview(Book book, User user, int rating, String comment) throws ReviewOutOfBoundsException {
    	// Throws ReviewOutOfBoundsException if rating is not between 1 and 5.
    	if (rating < 1 || rating > 5) {
            throw new ReviewOutOfBoundsException("Rating must be between 1 and 5.");
        }

        String reviewId = "R" + reviewCounter++;
        Review review = new Review(reviewId, user, rating, comment);

        book.getReviews().add(review);

        System.out.println("✅ Review added to book: " + book.getTitle());

        //Starts a separate thread to notify the author of the new review.
        Author author = book.getAuthor();
        NotificationThread notificationThread = new NotificationThread(author.getEmail(), book.getTitle());
        new Thread(notificationThread).start(); // Runs asynchronously
    }

    // Returns the list of reviews for a book sorted in reverse chronological order (newest first).
    public List<Review> getReviewsInReverseChronological(Book book) {
        List<Review> reviews = book.getReviews();
        reviews.sort((a, b) -> b.getTimestamp().compareTo(a.getTimestamp()));
        return reviews;
    }
}
