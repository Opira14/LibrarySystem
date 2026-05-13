package org.piraven;

public abstract class Item {
    protected String id;
    protected String title;
    protected Status status;

    private static int nextId = 1;

    public Item(String title, Status status) {
        this.id = String.format("04d", nextId++);
        this.title = title;
        this.status = status;
    }

    public enum Status {
        BORROWED, INSTORE, LOST
    }
}
