package org.piraven;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString(callSuper = true)
public class Student extends User {
    public Student(String name, List<Item> borrowedItems, Gender gender) {
        super(name, borrowedItems, gender);
        this.id = "S" + String.format("%04d", nextId++);
    }
}
