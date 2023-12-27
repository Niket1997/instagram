package org.instagram.exceptions.activity;

public class ActivityNotFoundException extends RuntimeException {
    public ActivityNotFoundException(String message) {
        super(message);
    }
}
