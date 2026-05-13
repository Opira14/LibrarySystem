package org.piraven;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString(callSuper = true)
public class Teacher extends User {

    public Teacher(String name, List<Item> borrowedItems, Gender gender) {
        super(name, borrowedItems, gender);
        this.id = "T" + String.format("%04d", nextId++);
    }
}
