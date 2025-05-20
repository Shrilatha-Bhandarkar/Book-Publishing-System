package com.booksystem.service;

import com.booksystem.entity.Author;
import com.booksystem.entity.Book;
import com.booksystem.exception.DuplicateBookException;
import com.booksystem.exception.InvalidInputException;
import com.booksystem.util.InputValidator;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Service class that handles operations related to Authors and their books.
 * Provides functionality for adding authors, publishing books, and retrieving
 * author data.
 */
public class AuthorService implements Serializable {
	private static final long serialVersionUID = 1L;

	// Stores all authors using userId as the key
	private Map<String, Author> authorMap = new HashMap<>();

	// Adds a new author after validating username
	public void addAuthor(String userId, String username, String email) throws InvalidInputException {
		InputValidator.validateName(username, "Username"); // Validate username
		Author author = new Author(userId, username, email); // Create author object
		authorMap.put(userId, author); // Add to map
		System.out.println("Author added: " + username);
	}

	// Returns the author by ID, or null if not found
	public Author getAuthorById(String authorId) {
		return authorMap.get(authorId);
	}

	// Publishes a new book for an author after checking for duplicate titles
	public void publishBook(String authorId, String bookId, String title, String content)
			throws InvalidInputException, DuplicateBookException {

		InputValidator.validateName(title, "Book Title"); // Validate book title

		Author author = authorMap.get(authorId); // Get the author
		if (author == null) {
			System.out.println("Author not found");
			return;
		}

		// Check for duplicate book titles for the same author
		for (Book b : author.getPublishedBooks()) {
			if (b.getTitle().equalsIgnoreCase(title)) {
				throw new DuplicateBookException("Duplicate book title: " + title);
			}
		}

		// Create new book and add to author's published books
		Book book = new Book(bookId, title, content, author);
		author.getPublishedBooks().add(book);
		System.out.println("Book published: " + title);
	}

	// Returns the complete map of all authors
	public Map<String, Author> getAllAuthors() {
		return authorMap;
	}
}
