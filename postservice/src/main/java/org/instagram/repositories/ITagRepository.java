package org.instagram.repositories;

import org.instagram.entities.Tag;
import org.instagram.enums.ParentType;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ITagRepository extends CrudRepository<Tag, Long> {
    List<Tag> findAllByParentIdAndParentType(Long parentId, ParentType parentType);
}
