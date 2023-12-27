package org.instagram.records.like;

import org.instagram.enums.ParentType;

public record UpdateLikeRequest(Long userId, Long parentId, ParentType parentType) {
}
