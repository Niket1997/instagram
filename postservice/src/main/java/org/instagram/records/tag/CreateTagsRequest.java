package org.instagram.records.tag;

import org.instagram.enums.ParentType;

import java.util.List;

public record CreateTagsRequest(Long parentId, ParentType parentType, List<Long> userIds) {
}
