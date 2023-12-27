package org.instagram.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.instagram.entities.PostResource;
import org.instagram.exceptions.aws.CloudfrontSignedUrlGenerationFailedException;
import org.instagram.interfaces.postresource.IPostResourceService;
import org.instagram.records.postresource.CreatePostResourcesRequest;
import org.instagram.records.postresource.PostResourceResponse;
import org.instagram.repositories.IPostResourceRepository;
import org.instagram.utils.CloudfrontSignedUrlGenerator;
import org.instagram.utils.Snowflake;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class PostResourceService implements IPostResourceService {
    private final IPostResourceRepository postResourceRepository;
    private final Snowflake snowflake;
    private final CloudfrontSignedUrlGenerator cloudfrontSignedUrlGenerator;

    public List<PostResourceResponse> createPostResources(CreatePostResourcesRequest request) {
        List<PostResourceResponse> postResourceResponses = new ArrayList<>();
        for (int i = 0; i < request.resourceIds().size(); i++) {
            PostResource postResource = new PostResource();
            postResource.setId(snowflake.nextId());
            postResource.setPostId(request.postId());
            postResource.setUserId(request.userId());
            postResource.setResourceId(request.resourceIds().get(i));
            postResource.setSequenceNumber(i);
            postResourceResponses.add(getPostResourceResponse(postResource));
            postResourceRepository.save(postResource);
        }
        return postResourceResponses;
    }

    @Override
    public List<PostResourceResponse> getPostResourcesByPostId(Long postId) {
        List<PostResource> postResources = postResourceRepository.findAllByPostIdOrderBySequenceNumber(postId);
        List<PostResourceResponse> postResourceResponses = new ArrayList<>();
        postResources.forEach(postResource -> postResourceResponses.add(getPostResourceResponse(postResource)));
        return postResourceResponses;
    }

    @Override
    public void deletePostResourceById(Long postResourceId) {
        postResourceRepository.deleteById(postResourceId);
    }

    private PostResourceResponse getPostResourceResponse(PostResource postResource) {
        String resourceUrl;
        try {
            String resourceKey = postResource.getUserId() + "/" + postResource.getResourceId();
            resourceUrl = cloudfrontSignedUrlGenerator.generateSignedUrlForResource(resourceKey);
        } catch (Exception e) {
            log.error("Error generating signed url for resource {}", postResource.getResourceId());
            throw new CloudfrontSignedUrlGenerationFailedException("Error generating signed url for resource {}" + postResource.getResourceId());
        }

        return new PostResourceResponse(postResource.getId(), postResource.getPostId(), postResource.getUserId(), postResource.getResourceId(), postResource.getSequenceNumber(), resourceUrl);
    }
}
