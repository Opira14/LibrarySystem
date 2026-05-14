package org.piraven;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class LibraryTest {
    @Test
    @DisplayName("Recursive search by title")
    public void testsearchRecursive1() {
        Library library = new Library();

        Item book = new Book("Data", Item.Status.INSTORE, "1259302690123", Book.Genre.ROMANCE, "Alice");
        Item dvd = new DVD("Data Science Movie", Item.Status.INSTORE, "John Director", 120);
        Item magazine = new Magazine("Cooking", Item.Status.INSTORE, 10, "Food");

        List<Item> items = new ArrayList<>();
        items.add(book);
        items.add(dvd);
        items.add(magazine);
        List<Item> actual = library.searchRecursive("data", items, 0);
        Assertions.assertEquals(2, actual.size());
        Assertions.assertTrue(actual.contains(book));
        Assertions.assertTrue(actual.contains(dvd));
    }

    @Test
    @DisplayName("Recursive search with no match")
    public void testsearchRecursive2() {
        Library library = new Library();

        Item book = new Book("History", Item.Status.INSTORE, "3748926357193", Book.Genre.HISTORY, "Bob");
        Item dvd = new DVD("Romantic", Item.Status.INSTORE, "Skibiddi", 110);

        List<Item> items = new ArrayList<>();
        items.add(book);
        items.add(dvd);
        List<Item> actual = library.searchRecursive("science", items, 0);
        Assertions.assertTrue(actual.isEmpty());
    }

    @Test
    @DisplayName("Stream search with matching keyword")
    public void testsearchStream1() {
        Library library = new Library();

        Item book = new Book("Artificial", Item.Status.INSTORE, "1111111111111", Book.Genre.SCIENCEFICTION, "Harel");
        Item dvd = new DVD("Space", Item.Status.INSTORE, "Geo", 95);
        Item magazine = new Magazine("Fashiony", Item.Status.INSTORE, 22, "Media");

        List<Item> items = new ArrayList<>();
        items.add(book);
        items.add(dvd);
        items.add(magazine);
        library.setItems(items);
        List<Item> actual = library.searchStream("Artificial");
        Assertions.assertTrue(actual.contains(book));
    }

    @Test
    @DisplayName("Stream search with empty query")
    public void testsearchStream2() {
        Library library = new Library();

        Item book = new Book("World", Item.Status.INSTORE, "2222222222222", Book.Genre.HISTORY, "Roshan");
        Item dvd = new DVD("Ocean", Item.Status.INSTORE, "Poutine", 88);

        List<Item> items = new ArrayList<>();
        items.add(book);
        items.add(dvd);
        library.setItems(items);
        List<Item> actual = library.searchStream("");
        Assertions.assertTrue(actual.isEmpty());
    }

    @Test
    @DisplayName("Add valid item")
    public void testadd1() {
        Library library = new Library();
        library.setItems(new ArrayList<>());

        Item book = new Book("Scream", Item.Status.INSTORE, "1036489263748", Book.Genre.HORROR, "Robert Martin");
        book.setId("1001");
        boolean expected = true;
        boolean actual = library.add(book);
        Assertions.assertEquals(expected, actual);
        Assertions.assertTrue(library.getItems().contains(book));
    }

    @Test
    @DisplayName("Add item with invalid ID")
    public void testadd2() {
        Library library = new Library();
        library.setItems(new ArrayList<>());

        Item dvd = new DVD("Kawai", Item.Status.INSTORE, "Bibe", 169);
        dvd.setId("XYZ12");
        boolean expected = false;
        boolean actual = library.add(dvd);
        Assertions.assertEquals(expected, actual);
        Assertions.assertFalse(library.getItems().contains(dvd));
    }

    @Test
    @DisplayName("Remove existing item")
    public void testremove1() {
        Library library = new Library();
        library.setItems(new ArrayList<>());

        Item book = new Book("Design", Item.Status.INSTORE, "2837462098153", Book.Genre.MYSTERY, "Gomma");
        book.setId("1001");
        library.getItems().add(book);
        boolean expected = true;
        boolean actual = library.remove(book);
        Assertions.assertEquals(expected, actual);
        Assertions.assertFalse(library.getItems().contains(book));
    }

    @Test
    @DisplayName("Remove null item")
    public void testremove2() {
        Library library = new Library();
        library.setItems(new ArrayList<>());

        boolean expected = false;
        boolean actual = library.remove(null);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Load valid CSV data")
    public void testloadData1() {
        Library library = new Library();
        library.loadData();

        Assertions.assertFalse(library.getItems().isEmpty());
        Assertions.assertFalse(library.getUsers().isEmpty());
    }

    @Test
    @DisplayName("Load data contains at least one Student user")
    public void testloadData2() {
        Library library = new Library();
        library.loadData();

        Assertions.assertTrue(
                library.getUsers().values().stream()
                        .anyMatch(user -> user instanceof Student)
        );
    }

    @Test
    @DisplayName("items and users exist before backup")
    public void testbackupData1() {
        Library library = new Library();
        library.setItems(new ArrayList<>());
        library.setUsers(new HashMap<>());

        Item book = new Book("Block", Item.Status.INSTORE, "1037264982648", Book.Genre.THRILLER, "Skiidid Harel");
        book.setId("1001");
        User student = new Student("Alice", new ArrayList<>(), User.Gender.FEMALE);
        student.setId("S1001");
        library.getItems().add(book);
        library.getUsers().put(student.getId(), student);
        boolean expectedItems = true;
        boolean actualItems = !library.getItems().isEmpty();
        boolean expectedUsers = true;
        boolean actualUsers = !library.getUsers().isEmpty();
        Assertions.assertEquals(expectedItems, actualItems);
        Assertions.assertEquals(expectedUsers, actualUsers);
    }


    @Test
    @DisplayName("user has borrowed items stored correctly")
    public void testbackupData2() {
        Library library = new Library();
        library.setItems(new ArrayList<>());
        library.setUsers(new HashMap<>());

        Item dvd = new DVD("Babordilo", Item.Status.BORROWED, "Caven", 148);
        dvd.setId("2001");

        List<Item> borrowed = new ArrayList<>();
        borrowed.add(dvd);

        User teacher = new Teacher("Bob", borrowed, User.Gender.MALE);
        teacher.setId("T2001");

        library.getItems().add(dvd);
        library.getUsers().put(teacher.getId(), teacher);
        boolean expected = false;
        boolean actual = library.getUsers().values().stream()
                .anyMatch(user -> !user.getBorrowedItems().isEmpty());
        Assertions.assertEquals(expected, actual);
    }
}