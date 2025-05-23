package domain.model;

import java.util.ArrayList;

public class Book {
    private int id_Book;
    private String title;
    private Author author;
    private boolean isAvailable;

    private ArrayList<Genre> bookGenres;

    public Book(int id_Book, String title, Author author, boolean isAvailable, ArrayList<Genre> genresGenres) {
        this.id_Book = id_Book;
        this.title = title;
        this.author = author;
        this.isAvailable = isAvailable;
        this.bookGenres = new ArrayList<>();
    }
    public Book(){}

    public int getId_Book() {
        return id_Book;
    }

    public void setId_Book(int id_Book) {
        this.id_Book = id_Book;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    public ArrayList<Genre> getBookGenres() {
        return bookGenres;
    }

    public void setBookGenres(ArrayList<Genre> bookGenres) {
        this.bookGenres = bookGenres;
    }

    @Override
    public String toString() {
        return "Book{" +
                "id_Book=" + id_Book +
                ", title='" + title + '\'' +
                ", author=" + author +
                ", isAvailable=" + isAvailable +
                ", bookGenres=" + bookGenres +
                '}';
    }
}
