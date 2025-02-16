import java.util.ArrayList;

public class User {
    private int id;
    private String name;
    private String passwordHash;
    private boolean isAdmin;
    private ArrayList<Loan> loanHistory;

    public User(int id, String name, String password, boolean isAdmin) {
        this.id = id;
        this.name = name;
        this.passwordHash = MD5(password);
        this.isAdmin = isAdmin;
        this.loanHistory = new ArrayList<>();
    }

    public void removeFromLoanHistory(Loan loan) {
        loanHistory.remove(loan);
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public ArrayList<Loan> getLoanHistory() {
        return loanHistory;
    }

    public void addLoan(Loan loan) {
        loanHistory.add(loan);
    }

    private String MD5(String md5) {
        try {
            java.security.MessageDigest md = java.security.MessageDigest.getInstance("MD5");
            byte[] array = md.digest(md5.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : array) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (java.security.NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }
}