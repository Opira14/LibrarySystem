package org.piraven;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ItemTest {
    @Test
    @DisplayName("Status changes to BORROWED")
    public void testborrow1() {
        Item book = new Book("Java", Item.Status.INSTORE, "1238367290123", Book.Genre.FANTASY, "Carl");
        Item.Status expected = Item.Status.BORROWED;
        book.borrow();
        Item.Status actual = book.getStatus();
        Assertions.assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Status remains BORROWED")
    public void testborrow2() {
        Item book = new Book("Lmao", Item.Status.INSTORE, "1234567890264", Book.Genre.MYSTERY, "Benji");
        book.borrow();
        Item.Status expected = Item.Status.BORROWED;
        book.borrow();
        Item.Status actual = book.getStatus();
        Assertions.assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Status changes to INSTORE")
    public void testreturn1() {
        Item dvd = new DVD("Interstellar", Item.Status.BORROWED, "Bob", 169);
        Item.Status expected = Item.Status.INSTORE;
        dvd.returnItem();
        Item.Status actual = dvd.getStatus();
        Assertions.assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Status remains INSTORE")
    public void testreturn2() {
        Item magazine = new Magazine("National", Item.Status.INSTORE, 45, "NatGeo");
        magazine.returnItem();
        Item.Status expected = Item.Status.INSTORE;
        Item.Status actual = magazine.getStatus();
        Assertions.assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Status changes to LOST")
    public void testmarkLost1() {
        Item book = new Book("Clean Code", Item.Status.INSTORE, "1234567890123", Book.Genre.THRILLER, "Harel Skibbidi");
        Item.Status expected = Item.Status.LOST;
        book.markLost();
        Item.Status actual = book.getStatus();
        Assertions.assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Status remains LOST")
    public void testmarkLost2() {
        Item dvd = new DVD("Sigma King", Item.Status.LOST, "Roshan", 148);
        dvd.markLost();
        Item.Status expected = Item.Status.LOST;
        Item.Status actual = dvd.getStatus();
        Assertions.assertEquals(expected, actual);
    }
}