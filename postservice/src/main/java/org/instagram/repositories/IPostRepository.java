package org.instagram.repositories;

import org.instagram.entities.Post;
import org.springframework.data.repository.CrudRepository;

public interface IPostRepository extends CrudRepository<Post, Long> {
}
