package org.instagram.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.instagram.entities.PostResource;
import org.instagram.interfaces.postresource.IPostResourceService;
import org.instagram.records.postresource.CreatePostResourcesRequest;
import org.instagram.repositories.IPostResourceRepository;
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

    public List<PostResource> createPostResources(CreatePostResourcesRequest request) {
        List<PostResource> postResources = new ArrayList<>();
        for (int i = 0; i < request.resourceIds().size(); i++) {
            PostResource postResource = new PostResource();
            postResource.setId(snowflake.nextId());
            postResource.setPostId(request.postId());
            postResource.setUserId(request.userId());
            postResource.setResourceId(request.resourceIds().get(i));
            postResource.setSequenceNumber(i);
            postResources.add(postResource);
            postResourceRepository.save(postResource);
        }
        return postResources;
    }

    @Override
    public List<PostResource> getPostResourcesByPostId(Long postId) {
        return postResourceRepository.findAllByPostIdOrderBySequenceNumber(postId);
    }
}
