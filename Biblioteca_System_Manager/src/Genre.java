public class Genre {
    private String nameGenre;

    public Genre(String name) {
        this.nameGenre = name;
    }

    public String getName() {
        return nameGenre;
    }

    @Override
    public String toString() {
        return "Genre{" +
                "nameGenre='" + nameGenre + '\'' +
                '}';
    }
}