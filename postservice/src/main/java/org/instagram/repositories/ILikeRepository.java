package org.instagram.repositories;

import org.instagram.entities.Like;
import org.instagram.enums.ParentType;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ILikeRepository extends CrudRepository<Like, Long> {
    Like findByParentIdAndParentTypeAndUserId(Long parentId, ParentType parentType, Long userId);

    List<Like> findByParentIdAndParentTypeOrderByCreatedAtDesc(Long parentId, ParentType parentType);
}
