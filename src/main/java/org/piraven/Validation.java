package org.piraven;

public class Validation {
    /**
     * Checks if the name is valid
     * @param name the given name
     * @return if the name is valid or not
     */
    public static boolean isValidName(String name) {
        if (name == null || name.isBlank()) {
            return false;
        }

        boolean hasLetter = false;
        for (int i = 0; i < name.length(); i++) {
            char ch = name.charAt(i);
            if (Character.isLetter(ch)) {
                hasLetter = true;
            } else if (ch != ' ') {
                return false;
            }
        }

        return hasLetter;
    }

    /**
     * Check if the length of ISBN is valid or not
     * @param ISBN the given ISBN
     * @return if the length of ISBN is valid or not
     */
    public static boolean isValidISBN(String ISBN) {
        return ISBN != null && ISBN.matches("\\d{13}");
    }

    /**
     * Checks if the id is valid or not
     * @param id the given id
     * @return if the id is valid or not
     */
    public static boolean isValidId(String id) {
        if (id == null) {
            return false;
        }

        return id.matches("[STA]\\d{4}") || id.matches("\\d{4}");
    }
}