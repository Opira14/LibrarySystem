package org.piraven;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class UserTest {
    @Test
    @DisplayName("Student borrows available book -> Book borrowed successfully")
    public void testborrowItem1() {
        User student = new Student("John", new ArrayList<>(), User.Gender.MALE);
        Item book = new Book("Java", Item.Status.INSTORE, "1234567890123", Book.Genre.HISTORY, "Smith");
        student.borrowItem(book);
        Assertions.assertEquals(Item.Status.BORROWED, book.getStatus());
        Assertions.assertTrue(student.getBorrowedItems().contains(book));
    }

    @Test
    @DisplayName("Student borrows borrowed book -> Borrow operation fails")
    public void testborrowItem2() {
        User student = new Student("John", new ArrayList<>(), User.Gender.MALE);
        Item book = new Book("Java", Item.Status.BORROWED, "1234567890123", Book.Genre.HISTORY, "Smith");
        Assertions.assertThrows(ItemUnavailableException.class, () -> student.borrowItem(book));
    }

    @Test
    @DisplayName("User returns borrowed item -> Item returned successfully")
    public void testreturnItem1() {
        User student = new Student("John", new ArrayList<>(), User.Gender.MALE);
        Item book = new Book("Java", Item.Status.BORROWED, "1234567890123", Book.Genre.HISTORY, "Smith");

        student.getBorrowedItems().add(book);
        student.returnItem(book);

        Assertions.assertEquals(Item.Status.INSTORE, book.getStatus());
        Assertions.assertFalse(student.getBorrowedItems().contains(book));
    }

    @Test
    @DisplayName("User returns item not borrowed -> Return operation fails")
    public void testreturnItem2() {
        User student = new Student("John", new ArrayList<>(), User.Gender.MALE);
        Item book = new Book("Java", Item.Status.BORROWED, "1234567890123", Book.Genre.HISTORY, "Smith");

        Assertions.assertThrows(InvalidUserOperationException.class, () -> student.returnItem(book));
    }

    @Test
    @DisplayName("Search existing item title -> Matching item returned")
    public void testsearchItem1() {
        User student = new Student("John", new ArrayList<>(), User.Gender.MALE);

        Item book1 = new Book("Java", Item.Status.BORROWED, "1234567890123", Book.Genre.HORROR, "Smith");
        Item book2 = new Book("Python", Item.Status.BORROWED, "1234567890124", Book.Genre.HORROR, "David");

        student.getBorrowedItems().add(book1);
        student.getBorrowedItems().add(book2);

        List<Item> expected = List.of(book1);
        List<Item> actual = student.searchItem("Java");
        Assertions.assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Search with empty keyword -> Empty list returned")
    public void testsearchItem2() {
        User student = new Student("John", new ArrayList<>(), User.Gender.MALE);

        List<Item> expected = List.of();
        List<Item> actual = student.searchItem("");
        Assertions.assertEquals(expected, actual);
    }
}