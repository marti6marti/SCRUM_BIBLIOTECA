package domain.model;

public class Genre {
    private int id_Genere;
    private String nameGenre;



    public Genre(String nameGenre, int genereid) {
        this.nameGenre = nameGenre;
        this.id_Genere = genereid;
    }

    public String getNameGenre() {
        return nameGenre;
    }

    public void setNameGenre(String nameGenre) {
        this.nameGenre = nameGenre;
    }

    public int getId_Genere() {
        return id_Genere;
    }

    public void setId_Genere(int id_Genere) {
        this.id_Genere = id_Genere;
    }

    @Override
    public String toString() {
        return "Genre{" +
                "nameGenre='" + nameGenre + '\'' +
                ", id_Genere=" + id_Genere +
                '}';
    }
}