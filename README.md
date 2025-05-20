# 📚 Online Book Publishing & Review System

A console-based Java application simulating an online platform for authors to publish books, and users to review them — featuring OOP principles, custom exceptions, string manipulation, multithreading, and collections.

---

## ✨ Features

- 👤 **User & Author Management**
  - Register as a user or author
  - Authors can publish books
  - Users can write reviews

- 📖 **Book Publishing**
  - Validate book title using string manipulation
  - Prevent duplicate book titles
  - Auto-generate URL slugs for book titles

- 📝 **Book Reviews**
  - Users can rate (1-5) and review books
  - Reviews displayed in reverse chronological order
  - Average book rating calculated
  - **Real-time notifications** to authors when a new review is added (via multithreading)

- 🔎 **Search & Analytics**
  - Search books by title keywords
  - View top N books by average rating

- 💾 **Persistence**
  - Application state is saved and loaded using Java serialization
 
---

## 🧱 Tech Stack
- Java 8+
- OOP (Inheritance, Encapsulation, Polymorphism)
- Collections (`HashMap`, `ArrayList`)
- Multithreading
- Custom Exception Handling
- File I/O (Serialization)

---

## 🧩 Core Entities
| Class    | Description                             |
|-------   |-----------------------------------------|
| `User`   | Represents a platform user              |
| `Author` | Inherits from `User`, can publish books |
| `Book`   | Holds book details, author, and reviews |
| `Review` | Contains user review data               |

## ❗ Custom Exceptions
- `InvalidInputException` – invalid characters in title/username  
- `DuplicateBookException` – duplicate book title by the same author  
- `ReviewOutOfBoundsException` – rating not between 1 and 5

## 🔔 Multithreading (Notification System)
When a review is added, a `NotificationThread` simulates sending an email to the author:
Notification sent to author@example.com: Your book 'Java Basics' has a new review.

## 💡 Sample Functionalities
- 📘 **Publish a Book:**
Enter title: Clean Code
Generated Slug: clean-code
Book published successfully!

- 🌟 **Add Review:**
Rating (1-5): 5
Comment: Must-read for developers!
Notification sent to author@example.com...

- 🔍 **Search Books by Keyword:**
Enter keyword: code
Results:
Clean Code by Robert Martin

- 📊 **Top Rated Books:**
1. Clean Code - Avg. Rating: 4.8
2. Effective Java - Avg. Rating: 4.5

---

## File Structure
```src/com/booksystem/
├── entity/
│   ├── User.java
│   ├── Author.java
│   ├── Book.java
│   └── Review.java
├── exception/
│   ├── InvalidInputException.java
│   ├── DuplicateBookException.java
│   └── ReviewOutOfBoundsException.java
├── main/
│   └── App.java
├── service/
│   ├── AuthorService.java
│   ├── BookService.java
│   ├── ReviewService.java
│   └── UserService.java
├── util/
│   ├── ApplicationState.java
│   ├── DataStore.java
│   └── InputValidator.java
└── thread/
    └── NotificationThread.java
```
---

## 🚀 Getting Started
### 🔧 Prerequisites
- Java 8 or later
- IDE or terminal with Java support

### ▶️ Run the App
1. **Clone the repository**:
   ```bash
   git clone https://github.com/Shrilatha-Bhandarkar/Book-Publishing-System.git
   cd Book-Publishing-System
   ```

2. **Compile and run**:
   ```bash
   javac -d bin src/com/booksystem/**/*.java
   java -cp bin com.booksystem.main.App
   ```

3. **Persistence**:  
   On subsequent runs, the application will load saved data from `app_state.ser`.


---

