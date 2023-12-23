package org.instagram.interfaces.post;

import org.instagram.records.post.CreatePostRequest;
import org.instagram.records.post.PostResponse;
import org.instagram.records.post.UpdatePostRequest;

public interface IPostService {
    PostResponse createPost(CreatePostRequest request);

    PostResponse updatePost(UpdatePostRequest request);

    PostResponse getPostById(Long id);

    void deletePost(Long id);
}
