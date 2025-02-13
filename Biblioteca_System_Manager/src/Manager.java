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

        library.borrowBook(user.getName(), book.getTitle());
    }
}
