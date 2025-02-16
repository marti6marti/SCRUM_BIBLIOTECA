import java.util.Scanner;

public class Manager {
    public static void main(String[] args) {
        Library library = new Library();
        Scanner scanner = new Scanner(System.in);

        Author author = new Author(1, "Autor1");
        Genre genre = new Genre("Fantasy");
        Book book = new Book(1, "Harry Potter", author, genre);
        library.addBook(book);

        User user = new User(1, "Marti", "password123", false);
        library.addUser(user);

        System.out.println("Enter username:");
        String username = scanner.nextLine();
        System.out.println("Enter password:");
        String password = scanner.nextLine();

        User loggedInUser = library.getUsers().get(username);
        if (loggedInUser != null && loggedInUser.getPasswordHash().equals(MD5(password))) {
            if (loggedInUser.isAdmin()) {
                System.out.println("Welcome Admin!");
                //aqui deberian de haber funciones de admin

            } else {
                System.out.println("Welcome User!");
                //aqui deberian de haber funciones de user

            }
        } else {
            System.out.println("Invalid username or password");
        }
    }

    private static String MD5(String md5) {
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