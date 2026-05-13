package org.piraven;

import java.util.ArrayList;
import java.util.List;

public abstract class User {
    protected String id;
    protected String name;
    protected List<Item> borrowedItems;
    protected Gender gender;

    private static int nextId = 1;

    public User(String name, List<Item> borrowedItems, Gender gender) {
        this.id = String.format("%04d", nextId++);
        this.name = name;
        this.borrowedItems = new ArrayList<>();
        this.gender = gender;
    }

    /**
     * borrows an Item for the user if allowed
     * @param item the item to be borrowed
     * @throws BorrowLimitExceededException if the user reach borrow limit
     * @throws ItemUnavailableException if the item is not available
     */
    public void borrowItem(Item item)
            throws BorrowLimitExceededException, ItemUnavailableException {

        if (borrowedItems.size() >= getBorrowLimit()) {
            throw new BorrowLimitExceededException("Borrow limit reached");
        }

        if (!canBorrow(item)) {
            throw new ItemUnavailableException("User cannot borrow this item type");
        }

        if (item.getStatus() != Item.Status.INSTORE) {
            throw new ItemUnavailableException("Item is not available");
        }

        borrowedItems.add(item);
        item.borrow();
    }

    public void returnItem(Item item) {
        if (item == null) {
            throw new InvalidUserOperationException("Item cannot be null");
        }

        if (!borrowedItems.contains(item)) {
            throw new InvalidUserOperationException("This user did not borrow this item");
        }

        borrowedItems.remove(item);
        item.setStatus(Item.Status.INSTORE);
    }

    public abstract int getBorrowLimit();
    public abstract boolean canBorrow(Item item);

    public enum Gender {
        MALE, FEMALE
    }
}