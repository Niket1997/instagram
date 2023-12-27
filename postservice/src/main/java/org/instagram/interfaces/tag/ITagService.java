package org.instagram.interfaces.tag;

import org.instagram.entities.Tag;
import org.instagram.enums.ParentType;
import org.instagram.records.tag.CreateTagsRequest;

import java.util.List;

public interface ITagService {
    List<Tag> tagUsers(CreateTagsRequest request);

    List<Tag> getTagsByParentId(Long parentId, ParentType parentType);
}
