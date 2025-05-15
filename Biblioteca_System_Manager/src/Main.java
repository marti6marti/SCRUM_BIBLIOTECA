import domain.model.*;
import java.util.Scanner;
import ui.*;

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
        if (loggedInUser != null && loggedInUser.getPasswordHash().equals(Manager.MD5(password))) {
            if (loggedInUser.isAdmin()) {
                System.out.println("Welcome Admin!");
                Manager.adminMenu(library);
            } else {
                System.out.println("Welcome User!");
                Manager.userMenu(loggedInUser, library);
            }
        } else {
            System.out.println("Invalid username or password");
        }
    }
}