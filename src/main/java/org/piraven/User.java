package org.piraven;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public abstract class User {
    protected String id;
    protected String name;
    protected List<Item> borrowedItems;
    protected Gender gender;

    protected static int nextId = 1;

    public User(String name, List<Item> borrowedItems, Gender gender) {
        if (!Validation.isValidName(name)) {
            throw new IllegalArgumentException("Invalid user name");
        }

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
        item.setStatus(Item.Status.BORROWED);
    }

    /**
     * returns the item to the library
     * @param item the item to be returned
     */
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

    /**
     * search the item
     * @param query the keyword
     * @return the list of item containing keyword
     */
    public List<Item> searchItem(String query) {
        if (query == null || query.isEmpty()) {
            return List.of();
        }

        String keyword = query.toLowerCase();

        return borrowedItems.stream()
                .filter(item -> item.getTitle().toLowerCase().contains(keyword))
                .toList();
    }

    public abstract int getBorrowLimit();
    public abstract boolean canBorrow(Item item);

    public enum Gender {
        MALE, FEMALE
    }

    public static class UserComparator implements Comparator<User> {

        private final String field;

        public UserComparator(String field) {
            this.field = field;
        }

        @Override
        public int compare(User o1, User o2) {

            switch (field.toLowerCase()) {

                case "id" -> {
                    return o1.getId().compareTo(o2.getId());
                }

                case "name" -> {
                    return o1.getName().compareTo(o2.getName());
                }

                case "type" -> {
                    return o1.getClass().getSimpleName().compareTo(o2.getClass().getSimpleName());
                }

                default -> throw new IllegalArgumentException("Invalid field: " + field);
            }
        }
    }
}