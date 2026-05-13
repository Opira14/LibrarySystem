package org.piraven;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString(callSuper = true)
public class Admin extends User {
    public Admin(String name, List<Item> borrowedItems, Gender gender) {
        super(name, borrowedItems, gender);
        this.id = "A" + String.format("%04d", nextId++);
    }
}
