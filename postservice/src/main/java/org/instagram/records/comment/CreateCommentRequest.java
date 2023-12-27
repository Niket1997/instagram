package org.instagram.records.comment;

import org.instagram.enums.ParentType;

public record CreateCommentRequest(String text, Long userId, Long parentId, ParentType parentType) {
}
