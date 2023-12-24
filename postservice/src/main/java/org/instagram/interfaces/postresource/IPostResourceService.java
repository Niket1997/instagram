package org.instagram.interfaces.postresource;

import org.instagram.entities.PostResource;
import org.instagram.records.postresource.CreatePostResourcesRequest;

import java.util.List;

public interface IPostResourceService {
    List<PostResource> createPostResources(CreatePostResourcesRequest request);

    List<PostResource> getPostResourcesByPostId(Long postId);

    void deletePostResourceById(Long postResourceId);
}
