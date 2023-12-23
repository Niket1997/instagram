package org.instagram.exceptions.external;

public class ResourceUploadFailedException extends RuntimeException {
    public ResourceUploadFailedException(String message) {
        super(message);
    }

    public ResourceUploadFailedException(String message, Throwable cause) {
        super(message, cause);
    }

    public ResourceUploadFailedException(Throwable cause) {
        super(cause);
    }
}
