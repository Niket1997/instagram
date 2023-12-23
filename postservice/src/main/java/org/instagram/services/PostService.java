package org.instagram.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.instagram.exceptions.external.ResourceUploadFailedException;
import org.instagram.external.ResourceService;
import org.instagram.interfaces.post.IPostService;
import org.instagram.records.external.resourceservice.ValidateAndUpdateResourcesRequest;
import org.instagram.records.external.resourceservice.ValidateAndUpdateResourcesResponse;
import org.instagram.records.post.CreatePostRequest;
import org.instagram.records.post.PostResponse;
import org.instagram.records.post.UpdatePostRequest;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class PostService implements IPostService {
    private final ResourceService resourceService;

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
        ValidateAndUpdateResourcesResponse resourceServiceResponse = resourceService.validateAndUpdateResources(new ValidateAndUpdateResourcesRequest(request.userId(), request.resourceIds()));
        if (!resourceServiceResponse.success()) {
            throw new ResourceUploadFailedException("resources for following resource ids not uploaded correctly: " + resourceServiceResponse.failedResourceIds());
        }
        log.info("resourceIds uploaded successfully");
        return null;
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
