package org.instagram.interfaces.post;

import org.instagram.records.post.CreatePostRequest;
import org.instagram.records.post.PostResponse;
import org.instagram.records.post.UpdatePostRequest;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.List;

public interface IPostService {
    PostResponse createPost(CreatePostRequest request);

    PostResponse updatePost(Long postId, UpdatePostRequest request);

    PostResponse getPostById(Long id) throws IOException, InvalidKeySpecException, NoSuchAlgorithmException;

    List<PostResponse> getPostsByUserId(Long userId);

    void deletePost(Long id);
}
