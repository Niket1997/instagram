package org.instagram.interfaces.postresource;

import org.instagram.records.postresource.CreatePostResourcesRequest;
import org.instagram.records.postresource.PostResourceResponse;

import java.util.List;

public interface IPostResourceService {
    List<PostResourceResponse> createPostResources(CreatePostResourcesRequest request);

    List<PostResourceResponse> getPostResourcesByPostId(Long postId);

    void deletePostResourceById(Long postResourceId);
}
