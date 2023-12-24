package org.instagram.records.post;

import org.instagram.entities.PostResource;
import org.instagram.entities.Tag;

import java.util.List;

public record PostResponse(Long postId, Long userId, String caption, List<PostResource> resources,
                           List<Tag> taggedUsers) {
}
