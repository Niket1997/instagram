package org.instagram.interfaces.like;

import org.instagram.entities.Like;
import org.instagram.enums.ParentType;
import org.instagram.records.like.UpdateLikeRequest;

import java.util.List;

public interface ILikeService {
    void like(UpdateLikeRequest request);

    void unlike(UpdateLikeRequest request);

    List<Like> getLikes(Long parentId, ParentType parentType);
}
