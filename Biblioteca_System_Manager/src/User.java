import java.util.ArrayList;

public class User {
    private String name;
    private ArrayList<Loan> loanHistory;

    public User(String name) {
        this.name = name;
        this.loanHistory = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public ArrayList<Loan> getLoanHistory() {
        return loanHistory;
    }

    public void addLoan(Loan loan) {
        loanHistory.add(loan);
    }


}
