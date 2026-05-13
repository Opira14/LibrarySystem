package org.piraven;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Comparator;
import java.util.List;

@Getter
@Setter
@ToString(callSuper = true)
public class Teacher extends User {

    public Teacher(String name, List<Item> borrowedItems, Gender gender) {
        super(name, borrowedItems, gender);
        this.id = "T" + String.format("%04d", nextId++);
    }

    @Override
    public int getBorrowLimit() {
        return Constants.MAX_ITEM_TEACHER;
    }

    @Override
    public boolean canBorrow(Item item) {
        return item != null
                && item.getStatus() == Item.Status.INSTORE;
    }

    public static class TeacherComparator implements Comparator<Teacher> {

        private final String field;

        public TeacherComparator(String field) {
            this.field = field;
        }

        @Override
        public int compare(Teacher o1, Teacher o2) {

            switch (field.toLowerCase()) {

                case "id" -> {
                    return o1.getId().compareTo(o2.getId());
                }

                case "name" -> {
                    return o1.getName().compareTo(o2.getName());
                }

                default -> throw new IllegalArgumentException("Invalid field: " + field);
            }
        }
    }
}
