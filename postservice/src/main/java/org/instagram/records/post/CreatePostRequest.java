package org.instagram.records.post;

import java.util.List;

public record CreatePostRequest(Long userId, String caption, List<Long> resourceIds, List<Long> taggedUserIds) {
}
