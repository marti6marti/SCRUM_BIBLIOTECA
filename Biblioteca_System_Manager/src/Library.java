import java.util.ArrayList;
import java.util.HashMap;

public class Library {
    private ArrayList<Book> books;
    private HashMap<String, User> users;

    public Library() {
        books = new ArrayList<>();
        users = new HashMap<>();
    }

    public void addBook(Book book) {
        books.add(book);
    }

    public void addUser(User user) {
        users.put(user.getName(), user);
    }

    public void borrowBook(String userName, String bookTitle) {
        User user = users.get(userName);
        for (Book book : books) {
            if (book.getTitle().equalsIgnoreCase(bookTitle) && book.isAvailable()) {
                Loan loan = new Loan(user, book);
                user.addLoan(loan);
                System.out.println(userName + " borrowed " + bookTitle);
                return;
            }
        }
        System.out.println("Book not available");
    }
}