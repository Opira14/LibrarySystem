package org.piraven;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ValidationTest {
    @Test
    @DisplayName("Alphabetical full name -> Returns true")
    public void testisValidName1() {
        String name = "Alice Johnson";
        boolean expected = true;
        boolean actual = Validation.isValidName(name);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Name with special character -> Returns false")
    public void testisValidName2() {
        String name = "Mike@Brown";
        boolean expected = false;
        boolean actual = Validation.isValidName(name);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    @DisplayName("13-digit ISBN -> Returns true")
    public void testisValidISBN1() {
        String ISBN = "9781234567890";
        boolean expected = true;
        boolean actual = Validation.isValidISBN(ISBN);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    @DisplayName("ISBN shorter than 13 digits -> Returns false")
    public void testisValidISBN2() {
        String ISBN = "123456789";
        boolean expected = false;
        boolean actual = Validation.isValidISBN("123456789");
        Assertions.assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Valid student ID -> Returns true")
    public void testisValidId1() {
        String id = "S1024";
        boolean expected = true;
        boolean actual = Validation.isValidId(id);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Valid item ID -> Returns true")
    public void testisValidId2() {
        String id = "4587";
        boolean expected = true;
        boolean actual = Validation.isValidId(id);
        Assertions.assertEquals(expected, actual);
    }
}