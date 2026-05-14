package org.piraven;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Comparator;

@Getter
@Setter
@EqualsAndHashCode
@ToString
public abstract class Item {
    protected String id;
    protected String title;
    protected Status status;

    private static int nextId = 1;

    public Item(String title, Status status) {
        this.id = String.format("%04d", nextId++);
        this.title = title;
        this.status = status;
    }

    /**
     * marks the item as borrowed
     */
    public void borrow() {
        this.status = Status.BORROWED;
    }

    /**
     * marks the item as instore
     */
    public void returnItem() {
        this.status = Status.INSTORE;
    }

    /**
     * marks the item as lost
     */
    public void markLost() {
        this.status = Status.LOST;
    }

    public enum Status {
        BORROWED, INSTORE, LOST
    }
    public static class ItemComparator implements Comparator<Item> {

        private final String field;

        public ItemComparator(String field) {
            this.field = field;
        }

        @Override
        public int compare(Item o1, Item o2) {

            switch (field.toLowerCase()) {

                case "id" -> {
                    return o1.getId().compareTo(o2.getId());
                }

                case "title" -> {
                    return o1.getTitle().compareTo(o2.getTitle());
                }

                case "status" -> {
                    return o1.getStatus().compareTo(o2.getStatus());
                }

                default -> throw new IllegalArgumentException("Invalid field: " + field);
            }
        }
    }
}
