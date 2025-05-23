package domain.model;

import java.util.ArrayList;

public class User {
    private int id_User;
    private String name;
    private String passwordHash;
    private boolean isAdmin;
    private ArrayList<Loan> loanHistory;

    public User(int id_User, String name, String passwordHash, boolean isAdmin, ArrayList<Loan> loanHistory) {
        this.id_User = id_User;
        this.name = name;
        this.passwordHash = passwordHash;
        this.isAdmin = isAdmin;
        this.loanHistory = new ArrayList<>();
    }

    public User(){}

    public int getId_User() {
        return id_User;
    }

    public void setId_User(int id_User) {
        this.id_User = id_User;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }

    public ArrayList<Loan> getLoanHistory() {
        return loanHistory;
    }

    public void setLoanHistory(ArrayList<Loan> loanHistory) {
        this.loanHistory = loanHistory;
    }

    @Override
    public String toString() {
        return "User{" +
                "id_User=" + id_User +
                ", name='" + name + '\'' +
                ", passwordHash='" + passwordHash + '\'' +
                ", isAdmin=" + isAdmin +
                ", loanHistory=" + loanHistory +
                '}';
    }
}