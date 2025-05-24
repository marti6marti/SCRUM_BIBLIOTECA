import domain.model.*;
import domain.implementDAO.UserCRUD;
import ui.Manager;
import java.sql.SQLException;
import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        UserCRUD userCRUD = new UserCRUD();

        System.out.println("Enter username:");
        String username = scanner.nextLine();
        System.out.println("Enter password:");
        String password = scanner.nextLine();

        try {
            User loggedInUser = userCRUD.getAll().stream()
                    .filter(u -> u.getName().equals(username) &&
                            u.getPasswordHash().equals(Manager.MD5(password)))
                    .findFirst()
                    .orElse(null);

            if (loggedInUser != null) {
                if (loggedInUser.isAdmin()) {
                    System.out.println("Welcome Admin!");
                    Manager.adminMenu();
                } else {
                    System.out.println("Welcome User!");
                    Manager.userMenu(loggedInUser);
                }
            } else {
                System.out.println("Invalid username or password");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
