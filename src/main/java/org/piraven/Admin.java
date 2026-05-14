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
}
