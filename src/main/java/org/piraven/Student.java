package org.piraven;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Comparator;
import java.util.List;

@Getter
@Setter
@ToString(callSuper = true)
public class Student extends User implements Comparator<Student> {
    public Student(String name, List<Item> borrowedItems, Gender gender) {
        super(name, borrowedItems, gender);
        this.id = "S" + String.format("%04d", nextId++);
    }

    @Override
    public int getBorrowLimit() {
        return Constants.MAX_BOOKS_STUDENT;
    }

    @Override
    public boolean canBorrow(Item item) {
        if (!(item instanceof Book)) {
            return false;
        }

        return item.getStatus() == Item.Status.INSTORE;
    }

    @Override
    public int compare(Student o1, Student o2) {
        String field = "";

        switch (field.toLowerCase()) {
            case "id" -> {
                return o1.id.compareTo(o2.id);
            }

            case "name" -> {
                return o1.name.compareTo(o2.name);
            }
        }

        return 0;
    }
}
