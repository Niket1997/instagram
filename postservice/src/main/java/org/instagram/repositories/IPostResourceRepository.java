package org.instagram.repositories;

import org.instagram.entities.PostResource;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface IPostResourceRepository extends CrudRepository<PostResource, Long> {
    List<PostResource> findAllByPostIdOrderBySequenceNumber(Long postId);
}
