package org.piraven;

public class Validation {
    /**
     * Checks if the name is valid
     * @param name the given name
     * @return if the name is valid or not
     */
    public static boolean isValidName(String name) {
        if (name == null) {
            return false;
        }

        for (int i = 0; i < name.length(); i++) {
            if (Character.isLetter(name.charAt(i))) {
                return true;
            }
        }

        return false;
    }
}