package org.instagram.interfaces.post;

import org.instagram.records.post.CreatePostRequest;
import org.instagram.records.post.PostResponse;
import org.instagram.records.post.UpdatePostRequest;

import java.util.List;

public interface IPostService {
    PostResponse createPost(CreatePostRequest request);

    PostResponse updatePost(Long postId, UpdatePostRequest request);

    PostResponse getPostById(Long id);

    List<PostResponse> getPostsByUserId(Long userId);

    void deletePost(Long id);
}
