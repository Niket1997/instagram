package org.instagram.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.instagram.exceptions.external.ResourceUploadFailedException;
import org.instagram.exceptions.post.PostNotFoundException;
import org.instagram.interfaces.post.IPostService;
import org.instagram.records.post.CreatePostRequest;
import org.instagram.records.post.PostResponse;
import org.instagram.records.post.UpdatePostRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/v1/posts")
@Slf4j
@RequiredArgsConstructor
public class PostController {
    private final IPostService postService;

    @PostMapping
    public PostResponse createPost(@RequestBody CreatePostRequest request) {
        try {
            return postService.createPost(request);
        } catch (ResourceUploadFailedException e) {
            log.error(e.getMessage());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), e);
        }
    }

    @GetMapping("/{postId}")
    public PostResponse getPostById(@PathVariable("postId") Long postId) {
        try {
            return postService.getPostById(postId);
        } catch (PostNotFoundException e) {
            log.error(e.getMessage());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), e);
        }
    }

    @GetMapping("/user/{userId}")
    public List<PostResponse> getPostsByUserId(@PathVariable("userId") Long userId) {
        return postService.getPostsByUserId(userId);
    }

    @PutMapping("/{postId}")
    public PostResponse updatePost(@PathVariable("postId") Long postId, @RequestBody UpdatePostRequest request) {
        return postService.updatePost(postId, request);
    }

    @DeleteMapping("/{postId}")
    public void deletePost(@PathVariable("postId") Long postId) {
        postService.deletePost(postId);
    }
}
