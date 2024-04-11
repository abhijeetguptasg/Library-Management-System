package application;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class LibraryManagementSystem {
    private static final String BOOK_FILE = "books.txt";
    private static final String USER_FILE = "users.txt";
    private static final String ENCRYPTION_KEY = "secretKey";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("1. Admin Login");
            System.out.println("2. Exit");
            System.out.print("Enter choice: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    if (adminLogin()) {
                        adminSession(scanner);
                    } else {
                        System.out.println("Invalid credentials.");
                    }
                    break;
                case 2:
                    System.out.println("Exiting the system.");
                    System.exit(0);
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static boolean adminLogin() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter admin username: ");
        String username = scanner.nextLine();
        System.out.print("Enter admin password: ");
        String password = scanner.nextLine();

        // Check admin credentials (in a real system, you would use a secure authentication mechanism)
        return "admin".equals(username) && "admin123".equals(password);
    }

    private static void adminSession(Scanner scanner) {
        while (true) {
            System.out.println("\nAdmin Session");
            System.out.println("1. Add Book");
            System.out.println("2. View Books");
            System.out.println("3. Issue Book");
            System.out.println("4. Save and Exit");
            System.out.print("Enter choice: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    addBook(scanner);
                    break;
                case 2:
                    viewBooks();
                    break;
                case 3:
                    issueBook(scanner);
                    break;
                case 4:
                    saveAndExit();
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void addBook(Scanner scanner) {
        System.out.print("Enter Book Title: ");
        String title = scanner.nextLine();
        System.out.print("Enter Author: ");
        String author = scanner.nextLine();

        // Create a Book object
        Book book = new Book(title, author);

        // Save the book to the file
        saveObject(book, BOOK_FILE);
    }

    private static void viewBooks() {
        // Load and display books from the file
        List<Book> books = loadObjects(BOOK_FILE, Book.class);
        System.out.println("Books in the Library:");
        for (Book book : books) {
            System.out.println(book);
        }
    }

    private static void issueBook(Scanner scanner) {
        // Implement book issuing logic here
        System.out.println("Book Issuing functionality will be implemented here.");
    }

    private static void saveAndExit() {
        // Save any changes and exit the program
        System.out.println("Saving changes and exiting.");
        System.exit(0);
    }

    private static void saveObject(Serializable obj, String filename) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename))) {
            oos.writeObject(obj);
            System.out.println("Object saved successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static <T extends Serializable> List<T> loadObjects(String filename, Class<T> clazz) {
        List<T> objects = new ArrayList<>();

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filename))) {
            while (true) {
                Object obj = ois.readObject();
                if (obj == null) break;
                if (clazz.isInstance(obj)) {
                    objects.add(clazz.cast(obj));
                }
            }
        } catch (EOFException e) {
            // End of file reached
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return objects;
    }
}

class Book implements Serializable {
    private static final long serialVersionUID = 1L;

    private String title;
    private String author;

    public Book(String title, String author) {
        this.title = title;
        this.author = author;
    }

    @Override
    public String toString() {
        return "Book{" +
                "title='" + title + '\'' +
                ", author='" + author + '\'' +
                '}';
    }
}
