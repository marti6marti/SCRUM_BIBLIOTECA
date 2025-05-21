package domain.model;

public class Genre {
    private String nameGenre;
    private int genereid;


    public Genre(String nameGenre, int genereid) {
        this.nameGenre = nameGenre;
        this.genereid = genereid;
    }

    public String getNameGenre() {
        return nameGenre;
    }

    public void setNameGenre(String nameGenre) {
        this.nameGenre = nameGenre;
    }

    public int getGenereid() {
        return genereid;
    }

    public void setGenereid(int genereid) {
        this.genereid = genereid;
    }

    @Override
    public String toString() {
        return "Genre{" +
                "nameGenre='" + nameGenre + '\'' +
                ", genereid=" + genereid +
                '}';
    }
}