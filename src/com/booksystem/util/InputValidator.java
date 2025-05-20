package com.booksystem.util;

import com.booksystem.exception.InvalidInputException;

/**
 * Utility class for input validation, including name and email validation,
 * and helper method to generate URL-friendly slugs from book titles.
 */
public class InputValidator {

    // Validates that input has only letters, digits, and spaces
    public static void validateName(String input, String fieldName) throws InvalidInputException {
        if (!input.matches("[a-zA-Z0-9 ]+")) {
            throw new InvalidInputException(fieldName + " contains invalid characters.");
        }
    }

    // Validates email format using a simple regex pattern
    public static void validateEmail(String email) throws InvalidInputException {
        String emailRegex = "^[\\w-\\.]+@[\\w-]+\\.[a-zA-Z]{2,}$";
        if (email == null || !email.matches(emailRegex)) {
            throw new InvalidInputException("Invalid email format.");
        }
    }

    // Generates a URL slug from the book title by trimming, converting to lowercase, and replacing spaces with hyphens
    public static String generateSlug(String title) {
        return title.trim().toLowerCase().replaceAll(" +", "-");
    }
}
