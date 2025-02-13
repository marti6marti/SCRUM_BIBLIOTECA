import java.util.Scanner;

public class Manager {
    public static void main(String[] args) {
        Library library = new Library();
        Scanner scanner = new Scanner(System.in);

        Author author = new Author("Autor1");
        Genre genre = new Genre("Fantasy");
        Book book = new Book("Harry Potter", author, genre);
        library.addBook(book);

        User user = new User("Marti");
        library.addUser(user);

        System.out.println("Enter your name:");
        String userName = scanner.nextLine();
        System.out.println("Enter book title to borrow:");
        String bookTitle = scanner.nextLine();
        library.borrowBook(userName, bookTitle);
    }
}
