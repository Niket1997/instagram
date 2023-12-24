package org.instagram.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.instagram.entities.Tag;
import org.instagram.enums.ParentType;
import org.instagram.interfaces.tag.ITagService;
import org.instagram.records.tag.CreateTagsRequest;
import org.instagram.repositories.ITagRepository;
import org.instagram.utils.Snowflake;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class TagService implements ITagService {
    private final Snowflake snowflake;
    private final ITagRepository tagRepository;

    public List<Tag> tagUsers(CreateTagsRequest request) {
        List<Tag> tags = new ArrayList<>();
        for (Long userId : request.userIds()) {
            Tag tag = new Tag();
            tag.setId(snowflake.nextId());
            tag.setUserId(userId);
            tag.setParentId(request.parentId());
            tag.setParentType(request.parentType());
            tags.add(tag);
            tagRepository.save(tag);
        }
        return tags;
    }

    @Override
    public List<Tag> getTagsByParentId(Long parentId, ParentType parentType) {
        return tagRepository.findAllByParentIdAndParentType(parentId, parentType);
    }
}
