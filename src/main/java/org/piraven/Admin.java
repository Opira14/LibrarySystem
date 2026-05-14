package org.piraven;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString(callSuper = true)
public class Admin extends User implements Reportable {
    public Admin(String name, List<Item> borrowedItems, Gender gender) {
        super(name, borrowedItems, gender);
        this.id = "A" + String.format("%04d", nextId++);
    }

    @Override
    public int getBorrowLimit() {
        return Integer.MAX_VALUE;
    }

    @Override
    public boolean canBorrow(Item item) {
        return false;
    }

    @Override
    public void generateReport(List<Item> items) {
        System.out.println("=== BORROWED-ITEMS ===");

        items.stream()
                .filter(item -> item.getStatus() == Item.Status.BORROWED)
                .forEach(item ->
                        System.out.println(item.getTitle()));

        System.out.println("\n=== INSTORE-ITEMS ===");

        items.stream()
                .filter(item -> item.getStatus() == Item.Status.INSTORE)
                .forEach(item ->
                        System.out.println(item.getTitle()));

        System.out.println("\n=== LOST-ITEMS ===");

        items.stream()
                .filter(item -> item.getStatus() == Item.Status.LOST)
                .forEach(item ->
                        System.out.println(item.getTitle()));
    }
    /**
     * Admin triggers system backup.
     * Saves all users and items into CSV files.
     */
    public void backupData(Library library) {
        try (FileWriter writer = new FileWriter(Constants.USERS_CSV_PATH)) {
            for (User user : library.getUsers().values()) {
                String userType = user.getClass().getSimpleName();

                List<String> borrowedIds = new ArrayList<>();

                for (Item item : user.getBorrowedItems()) {
                    borrowedIds.add(item.getId());
                }

                String borrowedIdsString = String.join(";", borrowedIds);
                writer.write(user.getId() + "," + user.getName() + "," + userType + "," +
                        borrowedIdsString + "," + user.getGender() + "\n");
            }

        } catch (Exception e) {
            System.out.println("Failed to backup users: " + e.getMessage());
        }

        try (FileWriter writer = new FileWriter(Constants.ITEMS_CSV_PATH)) {
            for (Item item : library.getItems()) {
                if (item instanceof Book book) {
                    writer.write(book.getId() + "," + book.getClass().getSimpleName() + "," + book.getTitle() + "," +
                            book.getStatus() + "," + book.getISBN() + "," + book.getAuthor() + "," + book.getGenre() + "\n");
                } else if (item instanceof DVD dvd) {
                    writer.write(dvd.getId() + "," + dvd.getClass().getSimpleName() + "," + dvd.getTitle() + "," +
                            dvd.getStatus() + "," + dvd.getDirector() + "," + dvd.getDuration() + "\n");
                } else if (item instanceof Magazine mag) {
                    writer.write(mag.getId() + "," + mag.getClass().getSimpleName() + "," + mag.getTitle() + "," +
                            mag.getStatus() + "," + mag.getIssueNumber() + "," + mag.getPublisher() + "\n");
                }
            }

        } catch (Exception e) {
            System.out.println("Failed to backup items: " + e.getMessage());
        }

        System.out.println("Backup completed.");
    }
}
