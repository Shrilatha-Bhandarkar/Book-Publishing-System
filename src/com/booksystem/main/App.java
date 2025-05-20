package com.booksystem.main;

import com.booksystem.entity.*;
import com.booksystem.exception.*;
import com.booksystem.service.*;
import com.booksystem.thread.NotificationThread;
import com.booksystem.util.*;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Main application class for the Online Book Publishing & Review System.
 * Handles the application lifecycle including:
 */

public class App {
	// Scanner for user input from the console
	private static final Scanner scanner = new Scanner(System.in);

	// Holds the entire application state including all services and data
	private static ApplicationState systemData;

	// Service instances managing users, authors, books, and reviews respectively
	private static UserService userService = new UserService();
	private static AuthorService authorService = new AuthorService();
	private static BookService bookService = new BookService();
	private static ReviewService reviewService = new ReviewService();

	// Filename for serialized application state persistence
	private static final String DATA_FILE = "app_state.ser";

	// Counters to generate unique IDs for users and authors
	private static int userCounter = 1;
	private static int authorCounter = 1;

	/**
	 * Entry point of the application. Loads saved application state if available;
	 * otherwise initializes fresh services. Presents a menu to the user repeatedly
	 * until exit.
	 */
	public static void main(String[] args) {
		System.out.println("Welcome to the Online Book Publishing & Review System");

		// Try loading saved state, or initialize fresh if null
		Object loaded = DataStore.loadState(DATA_FILE);
		if (loaded == null) {
			systemData = new ApplicationState(); // Initialize fresh with new services
			System.out.println("Starting with a fresh system.");
		} else {
			systemData = (ApplicationState) loaded;
			System.out.println("System data restored from saved state.\n");
		}
		userService = systemData.userService;
		authorService = systemData.authorService;
		bookService = systemData.bookService;
		reviewService = systemData.reviewService;

		while (true) {
			showMenu();
			int choice = getIntInput("Enter choice: ");
			try {
				switch (choice) {
				case 1 -> registerUser();
				case 2 -> registerAuthor();
				case 3 -> publishBook();
				case 4 -> listBooks();
				case 5 -> addReview();
				case 6 -> viewReviews();
				case 7 -> searchBooks();
				case 8 -> topRatedBooks();
				case 9 -> saveAppState();
				case 10 -> loadAppState();
				case 11 -> getAllUsers();
				case 12 -> getIntAllAuthors();
				case 0 -> {
					saveAppState();
					System.out.println("Exiting. Goodbye!");
					return;
				}
				default -> System.out.println("Invalid choice.");
				}

			} catch (Exception e) {
				System.out.println("Error: " + e.getMessage());
			}
		}
	}

	// Displays the main menu options to the user
	private static void showMenu() {
		System.out.println("""
				*****************************
				    1. Register User
				    2. Register Author
				    3. Publish Book
				    4. List All Books
				    5. Add Review to Book
				    6. View Book Reviews
				    7. Search Books by Title
				    8. Top N Books by Rating
				    9. Save Application State
				    10. Load Application State
				    11. Get User
				    12. Get Author

				    0. Exit
				*****************************
				    """);
	}

	// Handles the registration of a new user by taking username and email input,
	// validates input, generates a user ID, and adds the user via UserService
	private static void registerUser() throws InvalidInputException {
		System.out.print("Enter username: ");
		String username = scanner.nextLine();
		InputValidator.validateName(username, "Username");

		System.out.print("Enter email: ");
		String email = scanner.nextLine();

		String userId = "U" + String.format("%03d", userCounter++);
		userService.addUser(userId, username, email);
		System.out.println("User registered with ID: " + userId);
	}

	// Handles the registration of a new author by taking name and email input,
	// validates input, generates an author ID, and adds the author via
	// AuthorService
	private static void registerAuthor() throws InvalidInputException {
		System.out.print("Enter author name: ");
		String username = scanner.nextLine();
		InputValidator.validateName(username, "Username");

		System.out.print("Enter email: ");
		String email = scanner.nextLine();

		String authorId = "A" + String.format("%03d", authorCounter++);
		authorService.addAuthor(authorId, username, email);
		System.out.println("Author registered with ID: " + authorId);
	}

	// Publishes a new book by an existing author after validating inputs,
	// generates a unique book ID, and adds the book to the system and author's list
	private static void publishBook() throws InvalidInputException, DuplicateBookException {
		System.out.print("Enter author ID: ");
		String authorId = scanner.nextLine();
		Author author = authorService.getAuthorById(authorId);

		System.out.print("Enter book title: ");
		String title = scanner.nextLine();
		InputValidator.validateName(title, "Title");

		System.out.print("Enter book content: ");
		String content = scanner.nextLine();

		Book book = new Book(UUID.randomUUID().toString(), title, content, author);
		bookService.addBook(book);
		author.getPublishedBooks().add(book);

		System.out.println("Book published successfully.");
	}

	// Lists all published books with their authors and the number of reviews each
	// has
	private static void listBooks() {
		Collection<Book> books = bookService.getAllBooks();
		if (books.isEmpty()) {
			System.out.println("No books published yet.");
			return;
		}

		for (Book book : books) {
			System.out.println("Book: " + book.getTitle() + " by " + book.getAuthor().getUsername() + " (Reviews: "
					+ book.getReviews().size() + ")");
		}
	}

	// Adds a review to a specified book by a user, validates rating bounds,
	// saves the review, and notifies the author asynchronously
	private static void addReview() throws ReviewOutOfBoundsException {
		System.out.print("Enter book title: ");
		String title = scanner.nextLine();
		Book book = bookService.getBookByTitle(title);

		System.out.print("Enter your user ID: ");
		String userId = scanner.nextLine();
		User user = userService.getUserById(userId);

		int rating = getIntInput("Enter rating (1-5): ");
		if (rating < 1 || rating > 5) {
			throw new ReviewOutOfBoundsException("Rating must be between 1 and 5.");
		}

		System.out.print("Enter comment: ");
		String comment = scanner.nextLine();

		Review review = new Review(UUID.randomUUID().toString(), user, rating, comment);
		reviewService.addReview(book, user, rating, comment);

		// Notify author via thread
		NotificationThread thread = new NotificationThread(book.getAuthor().getEmail(), book.getTitle());
		thread.start();

		System.out.println("Review added.");
	}

	// Displays all reviews of a book in reverse chronological order with rating and
	// comments
	private static void viewReviews() {
		System.out.print("Enter book title: ");
		String title = scanner.nextLine();
		Book book = bookService.getBookByTitle(title);

		List<Review> reviews = reviewService.getReviewsInReverseChronological(book);
		if (reviews.isEmpty()) {
			System.out.println("No reviews yet.");
		} else {
			for (Review r : reviews) {
				System.out.println("⭐ " + r.getRating() + "/5 by " + r.getUser().getUsername() + ": " + r.getComment()
						+ " [" + r.getTimestamp() + "]");
			}
		}
	}

	// Searches books by keyword in title and lists matching books with author names
	private static void searchBooks() {
		System.out.print("Enter keyword to search in titles: ");
		String keyword = scanner.nextLine().toLowerCase();
		List<Book> results = bookService.searchBooks(keyword);
		if (results.isEmpty()) {
			System.out.println("No books found.");
		} else {
			for (Book book : results) {
				System.out.println("Book Title:" + book.getTitle() + " by " + book.getAuthor().getUsername());
			}
		}
	}

	// Displays top N books sorted by average rating along with number of reviews
	private static void topRatedBooks() {
		int n = getIntInput("Enter number of top books to list: ");
		List<Book> topBooks = bookService.getTopNBooksByRating(n);
		if (topBooks.isEmpty()) {
			System.out.println("No reviews available yet.");
			return;
		}

		for (Book book : topBooks) {
			double avg = book.getReviews().stream().mapToInt(Review::getRating).average().orElse(0.0);
			System.out.printf("Book %s - %.2f stars (%d reviews)%n", book.getTitle(), avg, book.getReviews().size());
		}
	}

	// Lists all registered users with their IDs, usernames, and emails in tabular
	// format
	private static void getAllUsers() {
		Map<String, User> allUsers = userService.getAllUsers();
		if (allUsers.isEmpty()) {
			System.out.println("No users found.");
			return;
		}
		System.out.printf("%-10s %-15s %-25s%n", "User ID", "Username", "Email");
		System.out.println("----------------------------------------");
		for (Map.Entry<String, User> entry : allUsers.entrySet()) {
			User user = entry.getValue();
			System.out.printf("%-10s %-15s %-25s%n", entry.getKey(), user.getUsername(), user.getEmail());
		}
		System.out.println("----------------------------------------\n");

	}

	// Lists all registered authors with their IDs, usernames, and emails in tabular
	// format
	private static void getIntAllAuthors() {
		Map<String, Author> allAuthors = authorService.getAllAuthors();
		if (allAuthors.isEmpty()) {
			System.out.println("No authors found.");
			return;
		}
		System.out.printf("%-10s %-15s %-25s%n", "Author ID", "Username", "Email");
		System.out.println("----------------------------------------");
		for (Map.Entry<String, Author> entry : allAuthors.entrySet()) {
			Author author = entry.getValue();
			System.out.printf("%-10s %-15s %-25s%n", entry.getKey(), author.getUsername(), author.getEmail());
		}
		System.out.println("----------------------------------------\n");

	}

	// Saves the current application state (services and data) to a file
	private static void saveAppState() {
		ApplicationState state = new ApplicationState(userService, authorService, bookService, reviewService);
		DataStore.saveState(state, DATA_FILE);
	}

	// Loads the application state from a file and restores services data
	private static void loadAppState() {
		ApplicationState loaded = (ApplicationState) DataStore.loadState(DATA_FILE);
		if (loaded != null) {
			userService = loaded.userService;
			authorService = loaded.authorService;
			bookService = loaded.bookService;
			reviewService = loaded.reviewService;
		}
	}

	// Utility method to safely read an integer from user input with prompt and
	// validation
	private static int getIntInput(String prompt) {
		while (true) {
			try {
				System.out.print(prompt);
				return Integer.parseInt(scanner.nextLine().trim());
			} catch (NumberFormatException e) {
				System.out.println("Please enter a valid number.");
			}
		}
	}

}
