package org.piraven;

public class InvalidUserOperationException extends RuntimeException {
    public InvalidUserOperationException(String message) {
        super(message);
    }
}
