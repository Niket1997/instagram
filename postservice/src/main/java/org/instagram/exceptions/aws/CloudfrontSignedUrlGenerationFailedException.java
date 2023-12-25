package org.instagram.exceptions.aws;

public class CloudfrontSignedUrlGenerationFailedException extends RuntimeException {
    public CloudfrontSignedUrlGenerationFailedException(String message) {
        super(message);
    }
}
