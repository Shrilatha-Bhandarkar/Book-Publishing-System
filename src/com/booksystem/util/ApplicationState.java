package com.booksystem.util;

import com.booksystem.service.*;

import java.io.Serializable;

/**
 * Utility class that holds the overall application state by maintaining
 * instances of service classes. Supports both fresh initialization and
 * loading from saved state by accepting existing service instances.
 */
public class ApplicationState implements Serializable {
    private static final long serialVersionUID = 1L;

    public UserService userService;
    public AuthorService authorService;
    public BookService bookService;
    public ReviewService reviewService;

    // Default constructor to initialize fresh services
    public ApplicationState() {
        this.userService = new UserService();
        this.authorService = new AuthorService();
        this.bookService = new BookService();
        this.reviewService = new ReviewService();
    }
    
    // Constructor to set existing services (useful if loading saved state)
    public ApplicationState(UserService u, AuthorService a, BookService b, ReviewService r) {
        this.userService = u;
        this.authorService = a;
        this.bookService = b;
        this.reviewService = r;
    }
}
