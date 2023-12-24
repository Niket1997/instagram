package org.instagram.repositories;

import org.instagram.entities.Tag;
import org.springframework.data.repository.CrudRepository;

public interface ITagRepository extends CrudRepository<Tag, Long> {
}
