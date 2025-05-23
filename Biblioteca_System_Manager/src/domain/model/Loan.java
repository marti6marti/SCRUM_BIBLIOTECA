package domain.model;

import java.util.Date;

public class Loan {
    private int id_Loan;
    private User user;
    private Book book;
    private Date loanDate;
    private Date returnDate;

    public Loan(int id_Loan, User user, Book book, Date loanDate, Date returnDate) {
        this.id_Loan = id_Loan;
        this.user = user;
        this.book = book;
        this.loanDate = loanDate;
        this.returnDate = returnDate;
    }

    public int getId_Loan() {
        return id_Loan;
    }

    public void setId_Loan(int id_Loan) {
        this.id_Loan = id_Loan;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public Date getLoanDate() {
        return loanDate;
    }

    public void setLoanDate(Date loanDate) {
        this.loanDate = loanDate;
    }

    public Date getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(Date returnDate) {
        this.returnDate = returnDate;
    }

    @Override
    public String toString() {
        return "Loan{" +
                "id_Loan=" + id_Loan +
                ", user=" + user +
                ", book=" + book +
                ", loanDate=" + loanDate +
                ", returnDate=" + returnDate +
                '}';
    }
}