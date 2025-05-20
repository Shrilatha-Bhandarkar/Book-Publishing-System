package com.booksystem.service;

import com.booksystem.entity.Author;
import com.booksystem.entity.Book;

import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Service class that manages operations related to Books.
 * Provides functionality for adding books, searching by title or keyword,
 * retrieving books by ID or title, and fetching top-rated books.
 */
public class BookService implements Serializable {
    private static final long serialVersionUID = 1L;

 // Stores all books using bookId as the key
	private Map<String, Book> bookMap = new HashMap<>();
	
	 // Add book to map (called from AuthorService after successful publishing)
    public void addBook(Book book) {
        bookMap.put(book.getBookId(), book);
    }
    
    //Searches for books containing the given keyword in their titles among the list of authors' published books.
    public List<Book> getBooksByKeyword(String keyword, List<Author> authors) {
        List<Book> results = new ArrayList<>();

        for (Author author : authors) {
            for (Book book : author.getPublishedBooks()) {
                if (book.getTitle().toLowerCase().contains(keyword.toLowerCase())) {
                    results.add(book);
                }
            }
        }
        return results;
    }
    
    //Retrieves a book by its exact title (case-insensitive).
    public Book getBookByTitle(String title) {
        for (Book book : bookMap.values()) {
            if (book.getTitle().equalsIgnoreCase(title)) {
                return book;
            }
        }
        throw new NoSuchElementException("Book not found with title: " + title);
    }

    // Search books by keyword in title
    public List<Book> searchBooks(String keyword) {
        return bookMap.values().stream()
                .filter(book -> book.getTitle().toLowerCase().contains(keyword.toLowerCase()))
                .collect(Collectors.toList());
    }

    // Retrieves the top N books ranked by their average review rating. Only books with at least one review are considered.
    public List<Book> getTopNBooksByRating(int n) {
        return bookMap.values().stream()
                .filter(book -> !book.getReviews().isEmpty()) // Only consider books with reviews
                .sorted((b1, b2) -> {
                    double avg1 = b1.getReviews().stream().mapToInt(r -> r.getRating()).average().orElse(0);
                    double avg2 = b2.getReviews().stream().mapToInt(r -> r.getRating()).average().orElse(0);
                    return Double.compare(avg2, avg1); // Descending
                })
                .limit(n)
                .collect(Collectors.toList());
    }

    // Returns all books currently stored.
    public Collection<Book> getAllBooks() {
        return bookMap.values();
    }

    //etrieves a book by its unique ID.
    public Book getBookById(String id) {
        return bookMap.get(id);
    }
}