public class Book {
    private String title;
    private Author author;
    private Genre genre;
    private boolean isAvailable;

    public Book(String title, Author author, Genre genre) {
        this.title = title;
        this.author = author;
        this.genre = genre;
        this.isAvailable = true;
        author.addBook(this);
    }

    public String getTitle() {
        return title;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }
}
