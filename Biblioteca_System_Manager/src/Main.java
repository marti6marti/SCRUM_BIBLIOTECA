import domain.model.*;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Library library = new Library();
        Scanner scanner = new Scanner(System.in);

        Author author = new Author(6, "Autor1");
        Genre genre = new Genre("Fantasy");
        Book book = new Book(1, "Harry Potter", author, genre);
        library.addBook(book);

        User user = new User(1, "Marti", "password1234", false);
        User user2 = new User(2, "Serhii", "password0000", true);
        library.addUser(user2);
        library.addUser(user);

        System.out.println("Enter username:");
        String username = scanner.nextLine();
        System.out.println("Enter password:");
        String password = scanner.nextLine();

        User loggedInUser = library.getUsers().get(username);
        if (loggedInUser != null && loggedInUser.getPasswordHash().equals(MD5(password))) {
            if (loggedInUser.isAdmin()) {
                System.out.println("Welcome Admin!");
                adminMenu(library);

            } else {
                System.out.println("Welcome domain.model.User!");
                userMenu(loggedInUser , library);

            }
        } else {
            System.out.println("Invalid username or password");
        }
    }

    private static String MD5(String md5) {
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

    private static void adminMenu(Library library) {
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
                    System.out.println("All Books in the domain.model.Library:");
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
                        System.out.println("domain.model.Author not found.");
                        break;
                    }
                    System.out.println("Enter genre name:");
                    String genreName = scanner.nextLine();
                    Genre genre = new Genre(genreName);
                    Book newBook = new Book(bookId, bookTitle, author, genre);
                    library.addBook(newBook);
                    System.out.println("domain.model.Book added successfully.");
                    break;
                case 4:
                    System.out.println("Enter book ID to remove:");
                    int removeBookId = scanner.nextInt();
                    if (library.removeBookById(removeBookId)) {
                        System.out.println("domain.model.Book removed successfully.");
                    } else {
                        System.out.println("domain.model.Book not found.");
                    }
                    break;
                case 5:
                    System.out.println("Enter username:");
                    String userName = scanner.nextLine();
                    User user = library.getUsers().get(userName);
                    if (user != null) {
                        System.out.println("domain.model.Loan History: " + user.getLoanHistory());
                    } else {
                        System.out.println("domain.model.User  not found.");
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

    private static void userMenu(User loggedInUser , Library library) {
        Scanner scanner = new Scanner(System.in);
        boolean exit = false;

        while (!exit) {
            System.out.println("domain.model.User  Menu:");
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
                    if (library.returnBook(bookIdToReturn, loggedInUser )) {
                        System.out.println("domain.model.Book returned successfully.");
                    } else {
                        System.out.println("domain.model.Book not found or was not borrowed.");
                    }
                    break;
                case 4:
                    System.out.println("domain.model.Loan History: " + loggedInUser .getLoanHistory());
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