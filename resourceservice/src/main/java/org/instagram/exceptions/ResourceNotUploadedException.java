package org.instagram.exceptions;

public class ResourceNotUploadedException extends RuntimeException {
    public ResourceNotUploadedException(String message) {
        super(message);
    }

    public ResourceNotUploadedException(String message, Throwable cause) {
        super(message, cause);
    }

    public ResourceNotUploadedException(Throwable cause) {
        super(cause);
    }
}
