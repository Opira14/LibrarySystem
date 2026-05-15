package org.piraven;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.*;
import java.util.*;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public class Library {
    private List<Item> items;
    private Map<String, User> users;

    /**
     * Recursively searches for items' title contains the given query.
     * @param query the keyword
     * @param items the list of items
     * @param index the index of the item
     * @return a list of items matching the keyword
     */
    public List<Item> searchRecursive(String query, List<Item> items, int index) {
        List<Item> result = new ArrayList<>();

        if (items == null || index >= items.size()) {
            return result;
        }

        Item currentItem = items.get(index);
        if (currentItem.getTitle().toLowerCase().contains(query.toLowerCase())) {
            result.add(currentItem);
        }

        result.addAll(searchRecursive(query, items, index + 1));
        return result;
    }

    /**
     * Searches items using a keyword.
     * @param query the keyword
     * @return list of matching items
     */
    public List<Item> searchStream(String query) {
        if (query == null || query.isEmpty()) {
            return List.of();
        }

        String keyword = query.toLowerCase();
        return items.stream()
                .filter(item -> item.getTitle().toLowerCase().contains(keyword))
                .toList();
    }

    /**
     * Adds an item to the library.
     * @param item the item to add
     * @return if the item was added or not
     */
    public boolean add(Item item) {
        if (item == null) {
            return false;
        }

        if (!Validation.isValidId(item.getId())) {
            System.out.println("Invalid item ID");
            return false;
        }

        boolean exists = items.stream()
                .anyMatch(i -> i.getId().equals(item.getId()));
        if (exists) {
            System.out.println("Item already exists");
            return false;
        }

        return items.add(item);
    }

    /**
     * Removes an item from the library
     * @param item the item to remove
     * @return if the item was removed or not
     */
    public boolean remove(Item item) {
        if (items.contains(item)) {
            items.remove(item);
            return true;
        }

        return false;
    }

    /**
     *  loads users from CSV file into the library system
     */
    public void loadData() {
        items = new ArrayList<>();
        users = new HashMap<>();

        try {
            Scanner scanner = new Scanner(new File(Constants.ITEMS_CSV_PATH));
            scanner.nextLine();

            while (scanner.hasNext()) {
                String line = scanner.nextLine();
                String[] element = line.split(",");
                String id = element[0];
                String type = element[1];
                String title = element[2];
                Item.Status status = Item.Status.valueOf(element[3]);

                Item item = null;

                switch (type) {
                    case "Book" -> {
                        String isbn = element[4];
                        String author = element[5];
                        Book.Genre genre = Book.Genre.valueOf(element[6]);

                        item = new Book(title, status, isbn, genre, author);
                    }

                    case "DVD" -> {
                        String director = element[4];
                        int duration = Integer.parseInt(element[5]);

                        item = new DVD(title, status, director, duration);
                    }

                    case "Magazine" -> {
                        int issueNumber = Integer.parseInt(element[4]);
                        String publisher = element[5];

                        item = new Magazine(title, status, issueNumber, publisher);
                    }

                    default -> System.out.println("Invalid item type");
                }

                if (item != null) {
                    if (Validation.isValidId(id)) {
                        item.setId(id);
                        items.add(item);
                    } else {
                        System.out.println("Invalid item ID skipped: " + id);
                    }
                }
            }

            scanner.close();

            Scanner console = new Scanner(new File(Constants.USERS_CSV_PATH));
            console.nextLine();

            while (console.hasNext()) {
                String line = console.nextLine();
                String[] data = line.split(",");
                String id = data[0];
                String type = data[1];
                String name = data[2];

                List<Item> borrowedItem = new ArrayList<>();

                if (!data[3].isBlank()) {
                    String[] borrowedIds = data[3].split(";");
                    for (String borrowedId : borrowedIds) {
                        for (Item item : items) {
                            if (item.getId().equals(borrowedId)) {
                                borrowedItem.add(item);
                            }
                        }
                    }
                }

                User.Gender gender = User.Gender.valueOf(data[4]);
                User user = null;

                switch (type) {
                    case "Student" -> user = new Student(name, borrowedItem, gender);
                    case "Teacher" -> user = new Teacher(name, borrowedItem, gender);
                    case "Admin" -> user = new Admin(name, borrowedItem, gender);
                    default -> System.out.println("Invalid user type");
                }

                if (user != null) {
                    if (Validation.isValidId(id)) {
                        user.setId(id);
                        users.put(id, user);
                    } else {
                        System.out.println("Invalid user ID skipped: " + id);
                    }
                }
            }

            console.close();
            System.out.println("Data loaded successfully.");

        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Error loading data: " + e.getMessage());
        }
    }

    /**
     * Saves all users and items back into CSV files.
     */
    public void backupData() {
        try {
            FileWriter itemWriter = new FileWriter(Constants.ITEMS_CSV_PATH);
            itemWriter.write("id,type,title,status,ISBN,author,genre,director,duration,issueNumber,publisher\n");

            for (Item item : items) {
                if (item instanceof Book book) {
                    itemWriter.write(book.getId() + ",Book," + book.getTitle() + "," + book.getStatus() + "," + book.getISBN() + "," +
                            book.getAuthor() + "," + book.getGenre() + "\n");
                } else if (item instanceof DVD dvd) {
                    itemWriter.write(dvd.getId() + ",DVD," + dvd.getTitle() + "," + dvd.getStatus() + "," + dvd.getDirector() + "," + dvd.getDuration() + "\n");
                } else if (item instanceof Magazine mag) {
                    itemWriter.write(mag.getId() + ",Magazine," + mag.getTitle() + "," + mag.getStatus() + "," + mag.getIssueNumber() + "," + mag.getPublisher() + "\n");
                }
            }

            itemWriter.close();

            FileWriter userWriter = new FileWriter(Constants.USERS_CSV_PATH);
            userWriter.write("id,type,name,borrowedItems,gender\n");

            for (User user : users.values()) {
                StringBuilder borrowed = new StringBuilder();
                for (Item item : user.getBorrowedItems()) {
                    borrowed.append(item.getId()).append(";");
                }

                if (!borrowed.isEmpty()) {
                    borrowed.setLength(borrowed.length() - 1);
                }

                userWriter.write(user.getId() + "," + user.getClass().getSimpleName() + "," + user.getName() + "," + borrowed + "," + user.getGender() + "\n");
            }

            userWriter.close();
            System.out.println("Backup completed successfully.");

        } catch (IOException e) {
            System.out.println("Error while backing up data: " + e.getMessage());
        }
    }
}