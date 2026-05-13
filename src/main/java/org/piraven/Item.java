package org.piraven;

import lombok.Getter;

@Getter
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

    public void returnItem() {
        this.status = Status.INSTORE;
    }

    public void markLost() {
        this.status = Status.LOST;
    }

    public enum Status {
        BORROWED, INSTORE, LOST
    }
}
