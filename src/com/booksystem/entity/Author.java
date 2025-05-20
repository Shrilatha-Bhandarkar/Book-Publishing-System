package com.booksystem.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents an Author who can publish books.
 * Inherits common user properties from the User class.
 */
public class Author extends User {
    // List of books published by the author
    private List<Book> publishedBooks;

    // Constructor initializing author with inherited user details
    public Author(String userId, String username, String email) {
        super(userId, username, email);
        this.publishedBooks = new ArrayList<>();
    }

    // Returns the list of published books
    public List<Book> getPublishedBooks() {
        return publishedBooks;
    }

    // Adds a book to the author's list of published books
    public void addBook(Book book) {
        publishedBooks.add(book);
    }
}
