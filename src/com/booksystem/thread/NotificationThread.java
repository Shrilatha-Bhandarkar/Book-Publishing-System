package com.booksystem.thread;

/**
 * Thread class to send notification emails asynchronously.
 * Simulates sending a notification when a book receives a new review.
 */
public class NotificationThread extends Thread {
    private final String email;
    private final String bookTitle;

    public NotificationThread(String email, String bookTitle) {
        this.email = email;
        this.bookTitle = bookTitle;
    }

    // Runs the notification process with a simulated delay
    @Override
    public void run() {
        try {
            Thread.sleep(2000); // Simulate delay
            System.out.println("Notification sent to " + email + ": Your book '" + bookTitle + "' has a new review.");
        } catch (InterruptedException e) {
            System.out.println("Notification thread interrupted.");
        }
    }
}
