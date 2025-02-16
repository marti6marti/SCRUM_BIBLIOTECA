import java.util.Date;

public class Loan {
    private User user;
    private Book book;
    private Date loanDate;
    private Date returnDate;

    public Loan(User user, Book book) {
        this.user = user;
        this.book = book;
        this.loanDate = new Date();
        this.returnDate = null;
        book.setAvailable(false);
    }

    public void returnBook() {
        this.returnDate = new Date();
        book.setAvailable(true);
    }
    public Book getBook() {
        return book;
    }

    public Date getReturnDate() {
        return returnDate;
    }


    @Override
    public String toString() {
        return "Loan{" +
                "user=" + user +
                ", book=" + book +
                ", loanDate=" + loanDate +
                ", returnDate=" + returnDate +
                '}';
    }
}