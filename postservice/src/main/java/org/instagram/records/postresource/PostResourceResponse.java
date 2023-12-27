package org.instagram.records.postresource;

public record PostResourceResponse(Long id, Long postId, Long userId, Long resourceId, int sequenceNumber,
                                   String resourceUrl) {
}
