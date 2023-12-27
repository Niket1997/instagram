package org.instagram.repositories;

import org.instagram.entities.Comment;
import org.instagram.enums.ParentType;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ICommentRepository extends CrudRepository<Comment, Long> {
    List<Comment> getCommentsByParentIdAndParentTypeOrderByCreatedAtDesc(Long parentId, ParentType parentType);

    List<Comment> getCommentsByUserIdOrderByCreatedAtDesc(Long userId);
}
