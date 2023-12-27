package org.instagram.interfaces.comment;

import org.instagram.enums.ParentType;
import org.instagram.records.comment.CommentResponse;
import org.instagram.records.comment.CreateCommentRequest;

import java.util.List;

public interface ICommentService {
    CommentResponse createComment(CreateCommentRequest request);

    CommentResponse getCommentById(Long commentId);

    List<CommentResponse> getCommentsByParentIdAndParentType(Long parentId, ParentType parentType);

    List<CommentResponse> getCommentsByUserId(Long userId);

    void deleteComment(Long commentId);
}
