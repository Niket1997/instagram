package org.instagram.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.instagram.entities.Activity;
import org.instagram.entities.Comment;
import org.instagram.enums.ActivityType;
import org.instagram.enums.ActivityUpdateType;
import org.instagram.enums.ParentType;
import org.instagram.exceptions.comment.CommentNotFoundException;
import org.instagram.interfaces.activity.IActivityService;
import org.instagram.interfaces.comment.ICommentService;
import org.instagram.records.comment.CommentResponse;
import org.instagram.records.comment.CreateCommentRequest;
import org.instagram.repositories.ICommentRepository;
import org.instagram.utils.Snowflake;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class CommentService implements ICommentService {
    public final ICommentRepository commentRepository;
    public final IActivityService activityService;
    public final Snowflake snowflake;

    @Override
    public CommentResponse createComment(CreateCommentRequest request) {
        Comment comment = new Comment();
        comment.setId(snowflake.nextId());
        comment.setParentId(request.parentId());
        comment.setParentType(request.parentType());
        comment.setUserId(request.userId());
        comment.setText(request.text());
        commentRepository.save(comment);
        // create activity for the new comment
        Activity activity = activityService.createActivity(comment.getId());
        // update activity of the parent
        activityService.updateActivity(request.parentId(), ActivityType.COMMENT, ActivityUpdateType.INCREMENT);
        return getCommentResponse(comment, activity);
    }

    @Override
    public CommentResponse getCommentById(Long commentId) {
        Comment comment = commentRepository.findById(commentId).orElse(null);
        if (comment == null) {
            throw new CommentNotFoundException("no comment found for comment id " + commentId);
        }
        Activity activity = activityService.getActivity(commentId);
        return getCommentResponse(comment, activity);
    }

    @Override
    public List<CommentResponse> getCommentsByParentIdAndParentType(Long parentId, ParentType parentType) {
        List<Comment> comments = commentRepository.getCommentsByParentIdAndParentTypeOrderByCreatedAtDesc(parentId, parentType);
        List<CommentResponse> commentResponseList = new ArrayList<>();
        for (Comment comment : comments) {
            Activity activity = activityService.getActivity(comment.getId());
            commentResponseList.add(getCommentResponse(comment, activity));
        }

        return commentResponseList;
    }

    @Override
    public List<CommentResponse> getCommentsByUserId(Long userId) {
        List<Comment> comments = commentRepository.getCommentsByUserIdOrderByCreatedAtDesc(userId);
        List<CommentResponse> commentResponseList = new ArrayList<>();
        for (Comment comment : comments) {
            Activity activity = activityService.getActivity(comment.getId());
            commentResponseList.add(getCommentResponse(comment, activity));
        }

        return commentResponseList;
    }

    @Override
    public void deleteComment(Long commentId) {
        Comment comment = commentRepository.findById(commentId).orElse(null);
        if (comment == null) {
            throw new CommentNotFoundException("no comment found for comment id " + commentId);
        }
        commentRepository.delete(comment);
    }

    private CommentResponse getCommentResponse(Comment comment, Activity activity) {
        return new CommentResponse(comment.getId(), comment.getText(), comment.getUserId(), comment.getParentId(), comment.getParentType(), activity.getNumberOfLikes(), activity.getNumberOfComments(), comment.getCreatedAt(), comment.getUpdatedAt());
    }
}
