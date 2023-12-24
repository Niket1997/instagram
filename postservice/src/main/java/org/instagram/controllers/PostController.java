package org.instagram.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.instagram.exceptions.external.ResourceUploadFailedException;
import org.instagram.interfaces.post.IPostService;
import org.instagram.records.post.CreatePostRequest;
import org.instagram.records.post.PostResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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
}
