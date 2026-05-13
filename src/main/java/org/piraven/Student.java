package org.piraven;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Comparator;
import java.util.List;

@Getter
@Setter
@ToString(callSuper = true)
public class Student extends User {
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

    public static class StudentComparator implements Comparator<Student> {

        private final String field;

        public StudentComparator(String field) {
            this.field = field;
        }

        @Override
        public int compare(Student o1, Student o2) {

            switch (field.toLowerCase()) {

                case "id" -> {
                    return o1.getId().compareTo(o2.getId());
                }

                case "name" -> {
                    return o1.getName().compareTo(o2.getName());
                }

                default -> {
                    return 0;
                }
            }
        }
    }
}