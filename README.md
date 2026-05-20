# LibraryCatalog.java
# Generic Library Catalog System

> A Java console application that manages library items using generic classes and methods. Demonstrates type-safe, reusable code design through a real-world library management scenario.

---

## Overview

**GenericLibraryCatalog** showcases Java Generics in action — a single `Catalog<T>` class manages Books, DVDs, and Magazines without duplicating any logic. The system enforces type safety at compile time while remaining fully flexible for future item types.

---

## Features

| Feature | Description |
|---|---|
| Generic catalog | One `Catalog<T>` class handles all item types |
| Type-safe items | `LibraryItem<T>` base class with typed item IDs |
| Three item types | Book (Integer ID), DVD (Integer ID), Magazine (String ID) |
| Full CRUD | Add, remove, search, and display items |
| Error handling | Graceful messages for invalid operations |
| Input validation | Empty titles, null items, and invalid inputs caught |
| Automated tests | 7 built-in test scenarios covering all operations |
| Colored CLI | ANSI-styled terminal output for readability |

---

## Generic Architecture

```
LibraryItem<T>          ← Generic base class (T = itemID type)
├── Book                ← extends LibraryItem<Integer>
├── DVD                 ← extends LibraryItem<Integer>
└── Magazine            ← extends LibraryItem<String>

Catalog<T extends LibraryItem<?>>   ← Generic catalog
├── Catalog<Book>
├── Catalog<DVD>
└── Catalog<Magazine>
```

---

## Sample Output

```
  ╔══════════════════════════════════════════════════════╗
  ║         GENERIC LIBRARY CATALOG SYSTEM              ║
  ║     CS 1103  |  Java Generics Assignment            ║
  ╚══════════════════════════════════════════════════════╝

  ┌─────────────────────────────────────────────────────┐
  │    INITIALIZING CATALOG WITH SAMPLE DATA            │
  └─────────────────────────────────────────────────────┘
  + Added: Clean Code
  + Added: Effective Java
  + Added: The Pragmatic Programmer
  + Added: Inception
  + Added: Interstellar
  + Added: National Geographic
  + Added: Scientific American

  ╔══════════════════════════════════════════════════════╗
  ║  Book Catalog                                        ║
  ╠══════════════════════════════════════════════════════╣
  [BOOK] ID: 1     | Clean Code                    | Author: Robert C. Martin
  [BOOK] ID: 2     | Effective Java                | Author: Joshua Bloch
  [BOOK] ID: 3     | The Pragmatic Programmer      | Author: David Thomas
  ╚══════════════════════════════════════════════════════╝
  Total items: 3
```

---

## Project Structure

```
generic-library-catalog/
│
├── LibraryCatalog.java      # All classes in a single file
│
├── screenshots/
│   └── output.png           # Sample program output
│
└── README.md
```

---

## How to Run

### Prerequisites
- Java JDK 8 or higher

### Steps

```bash
# 1. Clone the repository
git clone https://github.com/TorunT/generic-library-catalog.git
cd generic-library-catalog

# 2. Compile
javac LibraryCatalog.java

# 3. Run
java LibraryCatalog
```

### Menu Options

```
1 → View all catalogs
2 → Add a new item (Book / DVD / Magazine)
3 → Remove an item by title
4 → Search for an item by title
5 → Run all automated test scenarios
0 → Exit
```

---

## Concepts Demonstrated

- **Generic classes** — `LibraryItem<T>` and `Catalog<T>` with type parameters
- **Bounded type parameters** — `<T extends LibraryItem<?>>` ensures catalog only accepts valid items
- **Generic inheritance** — `Book`, `DVD`, `Magazine` extend the generic base class with concrete types
- **Type safety** — compile-time checking prevents incompatible types from entering the catalog
- **Wildcard types** — `LibraryItem<?>` used in bounded parameter declaration
- **Exception handling** — `IllegalArgumentException` for invalid operations with descriptive messages
- **Java Collections** — `ArrayList<T>` and Stream API for catalog management
- **try-with-resources** — Scanner closed properly after use

---

## Test Scenarios (Menu Option 5)

| Test | Operation | Expected Result |
|---|---|---|
| 1 | Add Book and DVD | Items added successfully |
| 2 | View catalog | All items displayed |
| 3 | Search existing item | Item details shown |
| 4 | Search non-existing item | "Item not found" message |
| 5 | Remove existing item | Item removed |
| 6 | Remove non-existing item | Exception caught, error shown |
| 7 | Add item with empty title | Exception caught, error shown |



## References

- Eck, D. J. (2022). *Introduction to programming using Java version 9, JavaFX edition*. https://math.hws.edu/javanotes/
- Divertitto, A. (2022). *Java generics: How to use angled brackets in practice*. CodeGym. https://codegym.cc/groups/posts/generics-in-java
- Kumar, A. (2023). *Mastering generics in Java*. Tech Thoughts Explorer. https://techthoughtsexplorer.hashnode.dev/mastering-generics-in-java-a-comprehensive-guide-for-java-developers

