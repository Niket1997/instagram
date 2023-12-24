package org.instagram.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.instagram.entities.Post;
import org.instagram.entities.PostResource;
import org.instagram.entities.Tag;
import org.instagram.enums.ParentType;
import org.instagram.exceptions.external.ResourceServiceException;
import org.instagram.exceptions.external.ResourceUploadFailedException;
import org.instagram.external.ResourceService;
import org.instagram.interfaces.post.IPostService;
import org.instagram.interfaces.postresource.IPostResourceService;
import org.instagram.interfaces.tag.ITagService;
import org.instagram.records.external.resourceservice.ValidateAndUpdateResourcesRequest;
import org.instagram.records.external.resourceservice.ValidateAndUpdateResourcesResponse;
import org.instagram.records.post.CreatePostRequest;
import org.instagram.records.post.PostResponse;
import org.instagram.records.post.UpdatePostRequest;
import org.instagram.records.postresource.CreatePostResourcesRequest;
import org.instagram.records.tag.CreateTagsRequest;
import org.instagram.utils.Snowflake;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class PostService implements IPostService {
    private final ResourceService resourceService;
    private final IPostResourceService postResourceService;
    private final ITagService tagService;

    private final Snowflake snowflake;

    @Override
    public PostResponse createPost(CreatePostRequest request) {
        /*
         * 1. validate if resourceIds exist by calling resource service
         * 2. create & save post resource entities
         * 3. add entries in tags table
         * 4. create post
         * 5. publish a message to ON_POST_PUBLISHED Kafka topic
         * */
        // 1. validate if resourceIds exist by calling resource service
        ValidateAndUpdateResourcesResponse resourceServiceResponse;
        try {
            resourceServiceResponse = resourceService.validateAndUpdateResources(new ValidateAndUpdateResourcesRequest(request.userId(), request.resourceIds()));
        } catch (Exception e) {
            log.error("error occurred while uploading resources", e);
            throw new ResourceServiceException(e.getMessage());
        }

        if (!resourceServiceResponse.success()) {
            throw new ResourceUploadFailedException("resources for following resource ids not uploaded correctly: " + resourceServiceResponse.failedResourceIds());
        }

        Long postId = snowflake.nextId();

        // 2. create & save post resource entities
        CreatePostResourcesRequest createPostResourcesRequest = new CreatePostResourcesRequest(request.userId(), postId, request.resourceIds());
        List<PostResource> postResources = postResourceService.createPostResources(createPostResourcesRequest);

        // 3. add entries in tags table
        CreateTagsRequest createTagsRequest = new CreateTagsRequest(postId, ParentType.POST, request.taggedUserIds());
        List<Tag> tags = tagService.tagUsers(createTagsRequest);

        // 4. create post
        Post post = new Post();
        post.setId(postId);
        post.setUserId(request.userId());
        post.setCaption(request.caption());


        // 5. publish a message to ON_POST_PUBLISHED Kafka topic

        log.info("post with post id " + postId + " created successfully");
        return new PostResponse(postId, request.userId(), request.caption(), postResources, tags);
    }

    @Override
    public PostResponse updatePost(UpdatePostRequest request) {
        return null;
    }

    @Override
    public PostResponse getPostById(Long id) {
        return null;
    }

    @Override
    public void deletePost(Long id) {

    }
}
