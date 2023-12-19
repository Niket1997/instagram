package org.instagram.repositories;

import org.instagram.entities.Resource;
import org.springframework.data.repository.CrudRepository;

public interface IResourceRepository extends CrudRepository<Resource, Long> {
}
