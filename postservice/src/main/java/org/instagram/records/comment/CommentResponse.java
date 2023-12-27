package org.instagram.records.comment;

import org.instagram.enums.ParentType;

public record CommentResponse(Long id, String text, Long userId, Long parentId, ParentType parentType,
                              Long numLikes, Long numComments, Long createdAt, Long updatedAt) {
}
