package org.piraven;

import java.util.ArrayList;
import java.util.List;

public abstract class User {
    protected String id;
    protected String name;
    protected List<Item> borrowedItems;
    protected Gender gender;

    private static int nextId = 1;

    public User() {
        this.id = null;
        this.name = null;
        this.borrowedItems = new ArrayList<>();
        this.gender = null;
    }

    public User(String name, List<Item> borrowedItems, Gender gender) {
        this.id = String.format("04d", nextId++);
        this.name = name;
        this.borrowedItems = borrowedItems;
        this.gender = gender;
    }

    public enum Gender {
        MALE, FEMALE
    }
}
