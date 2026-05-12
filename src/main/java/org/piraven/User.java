package org.piraven;

import java.util.List;

public abstract class User {
    protected String id;
    protected String name;
    protected List<Item> borrowedItems;
    protected Gender gender;

    public enum Gender {
        MALE, FEMALE
    }
}
