package domain.model;

import java.util.ArrayList;

public class Author {
    private int id_Author;
    private String name;


    public Author(int id, String name) {
        this.id_Author = id;
        this.name = name;
    }

    public int getId_Author() {
        return id_Author;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Author{" +
                "id_Author=" + id_Author +
                ", name='" + name + '\'' +
                '}';
    }
}