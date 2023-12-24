package org.instagram.repositories;

import org.instagram.entities.PostResource;
import org.springframework.data.repository.CrudRepository;

public interface IPostResourceRepository extends CrudRepository<PostResource, Long> {
}
