package org.instagram.exceptions.like;

public class ParentNotFoundException extends RuntimeException {
    public ParentNotFoundException(String message) {
        super(message);
    }

    public ParentNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public ParentNotFoundException(Throwable cause) {
        super(cause);
    }
}
