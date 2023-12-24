package org.instagram.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.instagram.entities.Post;
import org.instagram.entities.PostResource;
import org.instagram.entities.Tag;
import org.instagram.enums.ParentType;
import org.instagram.exceptions.external.ResourceServiceException;
import org.instagram.exceptions.external.ResourceUploadFailedException;
import org.instagram.exceptions.post.PostNotFoundException;
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
import org.instagram.repositories.IPostRepository;
import org.instagram.utils.CloudfrontSignedUrlGenerator;
import org.instagram.utils.Snowflake;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class PostService implements IPostService {
    private final ResourceService resourceService;
    private final IPostResourceService postResourceService;
    private final IPostRepository postRepository;
    private final ITagService tagService;
    private final KafkaTemplate<String, PostResponse> postResponseKafkaTemplate;
    private final Snowflake snowflake;
    private final CloudfrontSignedUrlGenerator cloudfrontSignedUrlGenerator;

    @Value("${spring.kafka.topics.on-post-published}")
    private String onPostPublishedKafkaTopic;

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
        postRepository.save(post);
        log.info("post with post id " + postId + " created successfully");

        // 5. publish a message to ON_POST_PUBLISHED Kafka topic
        PostResponse postResponse = new PostResponse(postId, request.userId(), request.caption(), postResources, tags);
        postResponseKafkaTemplate.send(onPostPublishedKafkaTopic, postResponse);
        log.info("PostResponse message sent to kafka");
        return postResponse;
    }

    @Override
    public PostResponse updatePost(Long postId, UpdatePostRequest request) {
        Post post = postRepository.findById(postId).orElse(null);
        if (post == null) {
            throw new PostNotFoundException("post with id " + postId + " not found");
        }

        if (!request.caption().isEmpty()) {
            post.setCaption(request.caption());
            postRepository.save(post);
        }

        List<PostResource> postResources = postResourceService.getPostResourcesByPostId(postId);
        List<Tag> taggedUsers = tagService.getTagsByParentId(postId, ParentType.POST);

        return new PostResponse(postId, post.getUserId(), post.getCaption(), postResources, taggedUsers);
    }

    @Override
    public PostResponse getPostById(Long postId) throws IOException, InvalidKeySpecException {
        Post post = postRepository.findById(postId).orElse(null);
        if (post == null) {
            throw new PostNotFoundException("post with id " + postId + " not found");
        }

        List<PostResource> postResources = postResourceService.getPostResourcesByPostId(postId);
        List<Tag> taggedUsers = tagService.getTagsByParentId(postId, ParentType.POST);

        cloudfrontSignedUrlGenerator.getSignedUrl("123/1188105233620344832");

        return new PostResponse(postId, post.getUserId(), post.getCaption(), postResources, taggedUsers);
    }

    @Override
    public List<PostResponse> getPostsByUserId(Long userId) {
        List<Post> posts = postRepository.findAllByUserIdOrderByCreatedAtDesc(userId);
        List<PostResponse> userPostResponses = new ArrayList<>();

        posts.forEach(post -> {
            List<PostResource> postResources = postResourceService.getPostResourcesByPostId(post.getId());
            List<Tag> taggedUsers = tagService.getTagsByParentId(post.getId(), ParentType.POST);
            PostResponse postResponse = new PostResponse(post.getId(), post.getUserId(), post.getCaption(), postResources, taggedUsers);
            userPostResponses.add(postResponse);
        });

        log.info("posts with user id " + userId + " retrieved successfully");
        return userPostResponses;
    }

    @Override
    public void deletePost(Long id) {
        postRepository.deleteById(id);
    }
}
