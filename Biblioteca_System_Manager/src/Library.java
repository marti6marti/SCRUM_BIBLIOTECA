import java.util.ArrayList;
import java.util.HashMap;

public class Library {
    private HashMap<String, User> users;
    private ArrayList<Book> books;

    public Library() {
        users = new HashMap<>();
        books = new ArrayList<>();
    }

    public void addUser (User user) {
        users.put(user.getName(), user);
    }

    public HashMap<String, User> getUsers() {
        return users;
    }

    public void addBook(Book book) {
        books.add(book);
    }

    public ArrayList<Book> getBooks() {
        return books;
    }

    public boolean removeBookById(int id) {
        for (Book book : books) {
            if (book.getId() == id) {
                books.remove(book);
                return true;
            }
        }
        return false;
    }

    public Author getAuthorById(int id) {
        return null;
    }

    public void borrowBook(String username, int bookId) {
        User user = users.get(username);
        for (Book book : books) {
            if (book.getId() == bookId && book.isAvailable()) {
                Loan loan = new Loan(user, book);
                user.addLoan(loan);
                System.out.println("Book borrowed successfully.");
                return;
            }
        }
        System.out.println("Book not available or does not exist.");
    }

    public void allBooks() {
        for (Book book: books){
            System.out.println(book);
        }
    }

    public boolean returnBook(int bookId, User user) {
        for (Loan loan : user.getLoanHistory()) {
            if (loan.getBook().getId() == bookId) {
                loan.returnBook();
                user.removeFromLoanHistory(loan);
                return true;
            }
        }
        return false;
    }
}