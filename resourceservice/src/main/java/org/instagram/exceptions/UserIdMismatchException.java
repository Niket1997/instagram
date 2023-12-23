package org.instagram.exceptions;

public class UserIdMismatchException extends RuntimeException {
    public UserIdMismatchException(String message) {
        super(message);
    }

    public UserIdMismatchException(String message, Throwable cause) {
        super(message, cause);
    }

    public UserIdMismatchException(Throwable cause) {
        super(cause);
    }
}
