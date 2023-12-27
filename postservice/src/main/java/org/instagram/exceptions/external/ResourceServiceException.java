package org.instagram.exceptions.external;

public class ResourceServiceException extends RuntimeException {
    public ResourceServiceException(String message) {
        super(message);
    }

    public ResourceServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public ResourceServiceException(Throwable cause) {
        super(cause);
    }

    public ResourceServiceException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
