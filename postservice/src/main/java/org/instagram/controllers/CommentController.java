package org.instagram.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.instagram.enums.ParentType;
import org.instagram.interfaces.comment.ICommentService;
import org.instagram.records.comment.CommentResponse;
import org.instagram.records.comment.CreateCommentRequest;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/v1/comments")
public class CommentController {
    public final ICommentService commentService;

    @PostMapping
    public CommentResponse createComment(@RequestBody CreateCommentRequest request) {
        return commentService.createComment(request);
    }

    @GetMapping("/{commentId}")
    public CommentResponse getCommentById(@PathVariable("commentId") Long commentId) {
        return commentService.getCommentById(commentId);
    }

    @GetMapping("/{parentType}/{parentId}")
    public List<CommentResponse> getCommentsByParentIdAndParentType(@PathVariable("parentId") Long parentId, @PathVariable("parentType") ParentType parentType) {
        return commentService.getCommentsByParentIdAndParentType(parentId, parentType);
    }

    @GetMapping("/user/{userId}")
    public List<CommentResponse> getCommentsByUserId(@PathVariable("userId") Long userId) {
        return commentService.getCommentsByUserId(userId);
    }

    @DeleteMapping("/{commentId}")
    public void deleteComment(@PathVariable("commentId") Long commentId) {
        commentService.deleteComment(commentId);
        log.info("Comment with id {} deleted", commentId);
    }
}
