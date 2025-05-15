package ui;

import domain.model.*;
import java.util.Scanner;

public class Manager {
    public static String MD5(String md5) {
        try {
            java.security.MessageDigest md = java.security.MessageDigest.getInstance("MD5");
            byte[] array = md.digest(md5.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : array) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (java.security.NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void adminMenu(Library library) {
        Scanner scanner = new Scanner(System.in);
        boolean exit = false;

        while (!exit) {
            System.out.println("Admin Menu:");
            System.out.println("1. View all users");
            System.out.println("2. View all books");
            System.out.println("3. Add a new book");
            System.out.println("4. Remove a book");
            System.out.println("5. View loan history of a user");
            System.out.println("6. Exit");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    System.out.println("Users: " + library.getUsers().keySet());
                    break;
                case 2:
                    System.out.println("All Books in the library:");
                    if (library.getBooks().isEmpty()) {
                        System.out.println("No books available in the library.");
                    } else {
                        for (Book book : library.getBooks()) {
                            System.out.println(book);
                        }
                    }
                    break;
                case 3:
                    System.out.println("Enter book ID:");
                    int bookId = scanner.nextInt();
                    scanner.nextLine();
                    System.out.println("Enter book title:");
                    String bookTitle = scanner.nextLine();
                    System.out.println("Enter author ID:");
                    int authorId = scanner.nextInt();
                    Author author = library.getAuthorById(authorId);
                    if (author == null) {
                        System.out.println("Author not found.");
                        break;
                    }
                    System.out.println("Enter genre name:");
                    String genreName = scanner.nextLine();
                    Genre genre = new Genre(genreName);
                    Book newBook = new Book(bookId, bookTitle, author, genre);
                    library.addBook(newBook);
                    System.out.println("Book added successfully.");
                    break;
                case 4:
                    System.out.println("Enter book ID to remove:");
                    int removeBookId = scanner.nextInt();
                    if (library.removeBookById(removeBookId)) {
                        System.out.println("Book removed successfully.");
                    } else {
                        System.out.println("Book not found.");
                    }
                    break;
                case 5:
                    System.out.println("Enter username:");
                    String userName = scanner.nextLine();
                    User user = library.getUsers().get(userName);
                    if (user != null) {
                        System.out.println("Loan History: " + user.getLoanHistory());
                    } else {
                        System.out.println("User not found.");
                    }
                    break;
                case 6:
                    exit = true;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    public static void userMenu(User loggedInUser, Library library) {
        Scanner scanner = new Scanner(System.in);
        boolean exit = false;

        while (!exit) {
            System.out.println("User Menu:");
            System.out.println("1. View available books");
            System.out.println("2. Borrow a book");
            System.out.println("3. Return a book");
            System.out.println("4. View loan");
            System.out.println("5. Exit");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    System.out.println("Available Books: ");
                    for (Book book : library.getBooks()) {
                        if (book.isAvailable()) {
                            System.out.println(book.getTitle());
                        }
                    }
                    break;
                case 2:
                    System.out.println("Enter book ID to borrow:");
                    int bookIdToBorrow = scanner.nextInt();
                    library.borrowBook(loggedInUser.getName(), bookIdToBorrow);
                    break;
                case 3:
                    System.out.println("Enter book ID to return:");
                    int bookIdToReturn = scanner.nextInt();
                    if (library.returnBook(bookIdToReturn, loggedInUser)) {
                        System.out.println("Book returned successfully.");
                    } else {
                        System.out.println("Book not found or was not borrowed.");
                    }
                    break;
                case 4:
                    System.out.println("Loan History: " + loggedInUser.getLoanHistory());
                    break;
                case 5:
                    exit = true;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}