package org.piraven;

import lombok.Getter;
import lombok.Setter;

@Getter
public abstract class Item {
    protected String id;
    protected String title;
    @Setter protected Status status;

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
}
