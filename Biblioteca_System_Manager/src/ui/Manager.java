package ui;

import domain.implementDAO.*;
import domain.model.*;

import java.sql.SQLException;
import java.util.List;
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

    public static void adminMenu() {
        Scanner scanner = new Scanner(System.in);
        BookCRUD bookCRUD = new BookCRUD();
        UserCRUD userCRUD = new UserCRUD();
        LoanCRUD loanCRUD = new LoanCRUD();
        AuthorCRUD authorCRUD = new AuthorCRUD();
        boolean exit = false;

        while (!exit) {
            System.out.println("\nAdmin Menu:");
            System.out.println("1. View all users");
            System.out.println("2. View all books");
            System.out.println("3. Add a new book");
            System.out.println("4. Remove a book");
            System.out.println("5. View loan history of a user");
            System.out.println("6. Manage authors");
            System.out.println("7. Exit");

            int choice = scanner.nextInt();
            scanner.nextLine();

            try {
                switch (choice) {
                    case 1:
                        List<User> users = userCRUD.getAll();
                        users.forEach(System.out::println);
                        break;
                    case 2:
                        bookCRUD.getAll().forEach(System.out::println);
                        break;
                    case 3:
                        System.out.print("Enter title: ");
                        String title = scanner.nextLine();

                        System.out.println("Available authors:");
                        List<Author> authors = authorCRUD.getAll();
                        authors.forEach(a -> System.out.println(a.getId_Author() + ": " + a.getName()));

                        System.out.print("Enter author ID: ");
                        int authorId = scanner.nextInt();
                        scanner.nextLine();

                        Author author = authorCRUD.read(authorId);
                        if (author == null) {
                            System.out.println("Author not found.");
                            break;
                        }

                        Book book = new Book(0, title, author, true, null);
                        bookCRUD.create(book);
                        System.out.println("Book added.");
                        break;
                    case 4:
                        System.out.print("Enter book ID to remove: ");
                        int removeId = scanner.nextInt();
                        scanner.nextLine();
                        bookCRUD.delete(removeId);
                        System.out.println("Book deleted.");
                        break;
                    case 5:
                        System.out.print("Enter user name: ");
                        String uname = scanner.nextLine();
                        User user = userCRUD.getAll().stream()
                                .filter(u -> u.getName().equals(uname))
                                .findFirst().orElse(null);
                        if (user == null) {
                            System.out.println("User not found.");
                            break;
                        }
                        loanCRUD.getAll().stream()
                                .filter(l -> l.getUser().getId_User() == user.getId_User())
                                .forEach(System.out::println);
                        break;
                    case 6:
                        manageAuthors(scanner, authorCRUD);
                        break;
                    case 7:
                        exit = true;
                        break;
                    default:
                        System.out.println("Invalid choice.");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private static void manageAuthors(Scanner scanner, AuthorCRUD authorCRUD) throws SQLException {
        System.out.println("Author Management:");
        System.out.println("1. Create Author");
        System.out.println("2. Read Author");
        System.out.println("3. Update Author");
        System.out.println("4. Delete Author");
        System.out.println("5. List Authors");

        int choice = scanner.nextInt();
        scanner.nextLine();

        switch (choice) {
            case 1:
                System.out.print("Enter author name: ");
                String newName = scanner.nextLine();
                authorCRUD.create(new Author(0, newName));
                System.out.println("Author created.");
                break;
            case 2:
                System.out.print("Enter author ID: ");
                int readId = scanner.nextInt();
                scanner.nextLine();
                Author readAuthor = authorCRUD.read(readId);
                System.out.println(readAuthor != null ? readAuthor : "Author not found.");
                break;
            case 3:
                System.out.print("Enter author ID: ");
                int updId = scanner.nextInt();
                scanner.nextLine();
                System.out.print("Enter new name: ");
                String updName = scanner.nextLine();
                authorCRUD.update(new Author(updId, updName));
                System.out.println("Author updated.");
                break;
            case 4:
                System.out.print("Enter author ID to delete: ");
                int delId = scanner.nextInt();
                scanner.nextLine();
                authorCRUD.delete(delId);
                System.out.println("Author deleted.");
                break;
            case 5:
                List<Author> authors = authorCRUD.getAll();
                authors.forEach(System.out::println);
                break;
            default:
                System.out.println("Invalid choice.");
        }
    }

    public static void userMenu(User user) {
        Scanner scanner = new Scanner(System.in);
        BookCRUD bookCRUD = new BookCRUD();
        LoanCRUD loanCRUD = new LoanCRUD();
        boolean exit = false;

        while (!exit) {
            System.out.println("\nUser Menu:");
            System.out.println("1. View available books");
            System.out.println("2. Borrow a book");
            System.out.println("3. Return a book");
            System.out.println("4. View loan history");
            System.out.println("5. Exit");

            int choice = scanner.nextInt();
            scanner.nextLine();

            try {
                switch (choice) {
                    case 1:
                        bookCRUD.getAll().stream()
                                .filter(Book::isAvailable)
                                .forEach(System.out::println);
                        break;
                    case 2:
                        System.out.print("Enter book ID to borrow: ");
                        int bookId = scanner.nextInt();
                        scanner.nextLine();
                        Book bookToBorrow = bookCRUD.read(bookId);
                        if (bookToBorrow != null && bookToBorrow.isAvailable()) {
                            Loan loan = new Loan(0, user, bookToBorrow, new java.util.Date(), null);
                            loanCRUD.create(loan);

                            bookToBorrow.setAvailable(false);
                            bookCRUD.update(bookToBorrow);

                            System.out.println("Book borrowed.");
                        } else {
                            System.out.println("Book not available.");
                        }
                        break;
                    case 3:
                        System.out.print("Enter book ID to return: ");
                        int bookIdReturn = scanner.nextInt();
                        scanner.nextLine();
                        List<Loan> userLoans = loanCRUD.getAll();
                        Loan loanToReturn = userLoans.stream()
                                .filter(l -> l.getUser().getId_User() == user.getId_User()
                                        && l.getBook().getId_Book() == bookIdReturn
                                        && l.getReturnDate() == null)
                                .findFirst().orElse(null);
                        if (loanToReturn != null) {
                            loanToReturn.setReturnDate(new java.util.Date());
                            loanCRUD.update(loanToReturn);

                            Book returnedBook = bookCRUD.read(bookIdReturn);
                            returnedBook.setAvailable(true);
                            bookCRUD.update(returnedBook);

                            System.out.println("Book returned.");
                        } else {
                            System.out.println("No active loan found for this book.");
                        }
                        break;
                    case 4:
                        loanCRUD.getAll().stream()
                                .filter(l -> l.getUser().getId_User() == user.getId_User())
                                .forEach(System.out::println);
                        break;
                    case 5:
                        exit = true;
                        break;
                    default:
                        System.out.println("Invalid choice.");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
