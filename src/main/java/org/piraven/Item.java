package org.piraven;

public abstract class Item {
    protected String id;
    protected String title;
    protected Status status;

    public enum Status {
        BORROWED, INSTORE, LOST
    }
}
