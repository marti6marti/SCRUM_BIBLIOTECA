package domain.model;

import java.util.ArrayList;

public class Author {
    private int id;
    private String name;
    private ArrayList<Book> books;

    public Author(int id, String name) {
        this.id = id;
        this.name = name;
        this.books = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void addBook(Book book) {
        books.add(book);
    }

    public ArrayList<Book> getBooks() {
        return books;
    }

    @Override
    public String toString() {
        return "domain.model.Author{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", numberOfBooks=" + books.size() +
                '}';
    }
}