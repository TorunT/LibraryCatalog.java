import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * ============================================================
 *  Generic Library Catalog System
 *  CS 1103 | Unit 6 Programming Assignment
 * ============================================================
 *  Demonstrates generic classes and methods through a
 *  real-world library catalog application.
 *
 *  Classes:
 *  - LibraryItem<T>  : Generic item with title, author, itemID
 *  - Catalog<T>      : Generic catalog with add/remove/search
 *  - Book            : Concrete item type (extends LibraryItem)
 *  - DVD             : Concrete item type (extends LibraryItem)
 *  - Magazine        : Concrete item type (extends LibraryItem)
 *  - LibraryCatalog  : Main program with CLI
 * ============================================================
 */

// ── 1. Generic LibraryItem<T> base class ─────────────────────
/**
 * Generic base class representing any library item.
 * T is the type of the itemID (e.g., Integer, String).
 */
class LibraryItem<T> {

    private T      itemID;
    private String title;
    private String author;
    private String type;

    public LibraryItem(T itemID, String title, String author, String type) {
        if (title == null || title.trim().isEmpty())
            throw new IllegalArgumentException("Title cannot be empty.");
        if (author == null || author.trim().isEmpty())
            throw new IllegalArgumentException("Author cannot be empty.");

        this.itemID  = itemID;
        this.title   = title.trim();
        this.author  = author.trim();
        this.type    = type;
    }

    // Getters
    public T      getItemID() { return itemID;  }
    public String getTitle()  { return title;   }
    public String getAuthor() { return author;  }
    public String getType()   { return type;    }

    /**
     * Returns a formatted string with item details.
     */
    public String getDetails() {
        return String.format("  [%s] ID: %-5s | %-30s | Author: %s",
            type, itemID, title, author);
    }

    @Override
    public String toString() {
        return getDetails();
    }
}

// ── 2. Concrete item types ────────────────────────────────────

/** Represents a book in the library. */
class Book extends LibraryItem<Integer> {
    private String isbn;

    public Book(int itemID, String title, String author, String isbn) {
        super(itemID, title, author, "BOOK");
        this.isbn = isbn;
    }

    @Override
    public String getDetails() {
        return super.getDetails() + " | ISBN: " + isbn;
    }
}

/** Represents a DVD in the library. */
class DVD extends LibraryItem<Integer> {
    private int durationMinutes;

    public DVD(int itemID, String title, String director, int durationMinutes) {
        super(itemID, title, director, "DVD ");
        this.durationMinutes = durationMinutes;
    }

    @Override
    public String getDetails() {
        return super.getDetails() + " | Duration: " + durationMinutes + " min";
    }
}

/** Represents a magazine in the library. */
class Magazine extends LibraryItem<String> {
    private String issueDate;

    public Magazine(String itemID, String title, String publisher, String issueDate) {
        super(itemID, title, publisher, "MAG ");
        this.issueDate = issueDate;
    }

    @Override
    public String getDetails() {
        return super.getDetails() + " | Issue: " + issueDate;
    }
}

// ── 3. Generic Catalog<T> class ──────────────────────────────
/**
 * Generic catalog that stores and manages LibraryItem objects.
 * T must extend LibraryItem with any itemID type.
 */
class Catalog<T extends LibraryItem<?>> {

    private final List<T>  items;
    private final String   catalogName;

    // ANSI color codes for styled output
    private static final String RESET  = "\u001B[0m";
    private static final String BOLD   = "\u001B[1m";
    private static final String CYAN   = "\u001B[36m";
    private static final String GREEN  = "\u001B[32m";
    private static final String RED    = "\u001B[31m";
    private static final String YELLOW = "\u001B[33m";

    public Catalog(String catalogName) {
        this.catalogName = catalogName;
        this.items       = new ArrayList<>();
    }

    /**
     * Adds a new item to the catalog.
     * @param item The library item to add.
     */
    public void addItem(T item) {
        if (item == null)
            throw new IllegalArgumentException("Cannot add a null item.");
        items.add(item);
        System.out.println(GREEN + "  + Added: " + item.getTitle() + RESET);
    }

    /**
     * Removes an item from the catalog by its title (case-insensitive).
     * Throws an exception if the item is not found.
     * @param title The title of the item to remove.
     */
    public void removeItem(String title) {
        String target = title.trim().toLowerCase();
        boolean removed = items.removeIf(
            item -> item.getTitle().toLowerCase().equals(target));

        if (!removed) {
            throw new IllegalArgumentException(
                "Item not found: \"" + title + "\". Nothing was removed.");
        }
        System.out.println(RED + "  - Removed: " + title + RESET);
    }

    /**
     * Retrieves and prints details of an item by its title.
     * @param title The title to search for.
     */
    public void getItemDetails(String title) {
        String target = title.trim().toLowerCase();
        T found = items.stream()
            .filter(item -> item.getTitle().toLowerCase().equals(target))
            .findFirst()
            .orElse(null);

        if (found == null) {
            System.out.println(RED + "  ! Item not found: \"" + title + "\"" + RESET);
        } else {
            System.out.println(CYAN + found.getDetails() + RESET);
        }
    }

    /**
     * Displays all items currently in the catalog.
     */
    public void displayCatalog() {
        System.out.println(CYAN + BOLD +
            "\n  ╔══════════════════════════════════════════════════════╗");
        System.out.printf("  ║  %-52s║%n", "  " + catalogName);
        System.out.println(
            "  ╠══════════════════════════════════════════════════════╣" + RESET);

        if (items.isEmpty()) {
            System.out.println(YELLOW + "  ║  (catalog is empty)" + RESET);
        } else {
            for (T item : items) {
                System.out.println("  " + item.getDetails());
            }
        }

        System.out.println(CYAN + BOLD +
            "  ╚══════════════════════════════════════════════════════╝" + RESET);
        System.out.println(YELLOW + "  Total items: " + items.size() + RESET);
    }

    /**
     * Returns the number of items in the catalog.
     */
    public int size() { return items.size(); }

    /**
     * Returns true if the catalog contains no items.
     */
    public boolean isEmpty() { return items.isEmpty(); }
}

// ── 4. Main program with CLI ──────────────────────────────────
public class LibraryCatalog {

    private static final String RESET  = "\u001B[0m";
    private static final String BOLD   = "\u001B[1m";
    private static final String CYAN   = "\u001B[36m";
    private static final String GREEN  = "\u001B[32m";
    private static final String RED    = "\u001B[31m";
    private static final String YELLOW = "\u001B[33m";

    private static final Scanner scanner = new Scanner(System.in);

    // Separate catalogs for each item type
    private static final Catalog<Book>     bookCatalog     = new Catalog<>("Book Catalog");
    private static final Catalog<DVD>      dvdCatalog      = new Catalog<>("DVD Catalog");
    private static final Catalog<Magazine> magazineCatalog = new Catalog<>("Magazine Catalog");

    public static void main(String[] args) {

        printBanner();
        seedTestData();
        runCLI();
        scanner.close();
    }

    // ── Seed initial data for testing ────────────────────────
    private static void seedTestData() {
        printSection("INITIALIZING CATALOG WITH SAMPLE DATA");

        // Books
        bookCatalog.addItem(new Book(1, "Clean Code",
            "Robert C. Martin", "978-0132350884"));
        bookCatalog.addItem(new Book(2, "Effective Java",
            "Joshua Bloch", "978-0134685991"));
        bookCatalog.addItem(new Book(3, "The Pragmatic Programmer",
            "David Thomas", "978-0201616224"));

        // DVDs
        dvdCatalog.addItem(new DVD(101, "Inception",    "Christopher Nolan", 148));
        dvdCatalog.addItem(new DVD(102, "Interstellar", "Christopher Nolan", 169));

        // Magazines
        magazineCatalog.addItem(new Magazine("MAG-01",
            "National Geographic", "National Geographic Society", "May 2026"));
        magazineCatalog.addItem(new Magazine("MAG-02",
            "Scientific American", "Springer Nature", "April 2026"));

        System.out.println(GREEN + "\n  Sample data loaded successfully." + RESET);
    }

    // ── CLI Loop ─────────────────────────────────────────────
    private static void runCLI() {
        boolean running = true;
        while (running) {
            printMainMenu();
            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1" -> viewAllCatalogs();
                case "2" -> addItemMenu();
                case "3" -> removeItemMenu();
                case "4" -> searchItemMenu();
                case "5" -> runTests();
                case "0" -> {
                    System.out.println(CYAN + "\n  Goodbye! Library catalog closed." + RESET);
                    running = false;
                }
                default -> System.out.println(RED + "  Invalid option. Please try again." + RESET);
            }
        }
    }

    // ── Menu displays ─────────────────────────────────────────
    private static void printMainMenu() {
        System.out.println(CYAN + BOLD + "\n  ── Main Menu ──────────────────────────" + RESET);
        System.out.println("  1. View all catalogs");
        System.out.println("  2. Add a new item");
        System.out.println("  3. Remove an item");
        System.out.println("  4. Search for an item");
        System.out.println("  5. Run test scenarios");
        System.out.println("  0. Exit");
        System.out.print(YELLOW + "  Enter choice: " + RESET);
    }

    private static void viewAllCatalogs() {
        bookCatalog.displayCatalog();
        dvdCatalog.displayCatalog();
        magazineCatalog.displayCatalog();
    }

    private static void addItemMenu() {
        System.out.println(CYAN + "\n  Add item to: 1=Book  2=DVD  3=Magazine" + RESET);
        System.out.print("  Choice: ");
        String type = scanner.nextLine().trim();

        try {
            switch (type) {
                case "1" -> {
                    System.out.print("  Title: ");  String t = scanner.nextLine();
                    System.out.print("  Author: "); String a = scanner.nextLine();
                    System.out.print("  ISBN: ");   String i = scanner.nextLine();
                    int id = bookCatalog.size() + 1;
                    bookCatalog.addItem(new Book(id, t, a, i));
                }
                case "2" -> {
                    System.out.print("  Title: ");    String t = scanner.nextLine();
                    System.out.print("  Director: "); String d = scanner.nextLine();
                    System.out.print("  Duration (min): "); int dur = Integer.parseInt(scanner.nextLine());
                    int id = 100 + dvdCatalog.size() + 1;
                    dvdCatalog.addItem(new DVD(id, t, d, dur));
                }
                case "3" -> {
                    System.out.print("  Title: ");     String t = scanner.nextLine();
                    System.out.print("  Publisher: "); String p = scanner.nextLine();
                    System.out.print("  Issue date: "); String dt = scanner.nextLine();
                    String id = "MAG-0" + (magazineCatalog.size() + 1);
                    magazineCatalog.addItem(new Magazine(id, t, p, dt));
                }
                default -> System.out.println(RED + "  Invalid type." + RESET);
            }
        } catch (IllegalArgumentException e) {
            System.out.println(RED + "  Error: " + e.getMessage() + RESET);
        }
    }

    private static void removeItemMenu() {
        System.out.print(CYAN + "\n  Remove from: 1=Book  2=DVD  3=Magazine — Choice: " + RESET);
        String type = scanner.nextLine().trim();
        System.out.print("  Enter title to remove: ");
        String title = scanner.nextLine();

        try {
            switch (type) {
                case "1" -> bookCatalog.removeItem(title);
                case "2" -> dvdCatalog.removeItem(title);
                case "3" -> magazineCatalog.removeItem(title);
                default  -> System.out.println(RED + "  Invalid type." + RESET);
            }
        } catch (IllegalArgumentException e) {
            System.out.println(RED + "  Error: " + e.getMessage() + RESET);
        }
    }

    private static void searchItemMenu() {
        System.out.print(CYAN + "\n  Search in: 1=Book  2=DVD  3=Magazine — Choice: " + RESET);
        String type = scanner.nextLine().trim();
        System.out.print("  Enter title to search: ");
        String title = scanner.nextLine();

        switch (type) {
            case "1" -> bookCatalog.getItemDetails(title);
            case "2" -> dvdCatalog.getItemDetails(title);
            case "3" -> magazineCatalog.getItemDetails(title);
            default  -> System.out.println(RED + "  Invalid type." + RESET);
        }
    }

    // ── Automated test scenarios ──────────────────────────────
    private static void runTests() {
        printSection("TEST SCENARIOS");

        // Test 1: Add items
        System.out.println(YELLOW + "\n  Test 1: Adding items" + RESET);
        bookCatalog.addItem(new Book(99, "Design Patterns", "Gang of Four", "978-0201633610"));
        dvdCatalog.addItem(new DVD(199, "The Matrix", "Wachowski Sisters", 136));

        // Test 2: View catalog
        System.out.println(YELLOW + "\n  Test 2: Viewing catalog" + RESET);
        bookCatalog.displayCatalog();

        // Test 3: Search existing item
        System.out.println(YELLOW + "\n  Test 3: Search existing item 'Clean Code'" + RESET);
        bookCatalog.getItemDetails("Clean Code");

        // Test 4: Search non-existing item
        System.out.println(YELLOW + "\n  Test 4: Search non-existing item 'Unknown Book'" + RESET);
        bookCatalog.getItemDetails("Unknown Book");

        // Test 5: Remove existing item
        System.out.println(YELLOW + "\n  Test 5: Remove 'Design Patterns'" + RESET);
        try {
            bookCatalog.removeItem("Design Patterns");
        } catch (IllegalArgumentException e) {
            System.out.println(RED + "  Error: " + e.getMessage() + RESET);
        }

        // Test 6: Remove non-existing item (error handling)
        System.out.println(YELLOW + "\n  Test 6: Remove non-existing item (expects error)" + RESET);
        try {
            bookCatalog.removeItem("Non Existing Book");
        } catch (IllegalArgumentException e) {
            System.out.println(RED + "  [CAUGHT] " + e.getMessage() + RESET);
        }

        // Test 7: Add item with empty title (validation)
        System.out.println(YELLOW + "\n  Test 7: Add item with empty title (expects error)" + RESET);
        try {
            bookCatalog.addItem(new Book(999, "", "Unknown", "000"));
        } catch (IllegalArgumentException e) {
            System.out.println(RED + "  [CAUGHT] " + e.getMessage() + RESET);
        }

        System.out.println(GREEN + BOLD + "\n  All test scenarios completed." + RESET);
    }

    // ── Helpers ───────────────────────────────────────────────
    private static void printBanner() {
        System.out.println(CYAN + BOLD);
        System.out.println("  ╔══════════════════════════════════════════════════════╗");
        System.out.println("  ║         GENERIC LIBRARY CATALOG SYSTEM              ║");
        System.out.println("  ║     CS 1103  |  Java Generics Assignment            ║");
        System.out.println("  ╚══════════════════════════════════════════════════════╝");
        System.out.println(RESET);
    }

    private static void printSection(String title) {
        System.out.println(CYAN + BOLD +
            "\n  ┌─────────────────────────────────────────────────────┐");
        System.out.printf("  │  %-51s│%n", "  " + title);
        System.out.println(
            "  └─────────────────────────────────────────────────────┘" + RESET);
    }
}
