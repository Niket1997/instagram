package org.instagram.records.postresource;

import java.util.List;

public record CreatePostResourcesRequest(Long userId, Long postId, List<Long> resourceIds) {
}
