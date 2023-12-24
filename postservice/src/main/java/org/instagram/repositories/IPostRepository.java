package org.instagram.repositories;

import org.instagram.entities.Post;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface IPostRepository extends CrudRepository<Post, Long> {
    List<Post> findAllByUserIdOrderByCreatedAtDesc(Long userId);
}
