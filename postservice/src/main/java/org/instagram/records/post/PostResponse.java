package org.instagram.records.post;

import org.instagram.entities.Tag;
import org.instagram.records.postresource.PostResourceResponse;

import java.util.List;

public record PostResponse(Long postId, Long userId, String caption, List<PostResourceResponse> resources,
                           List<Tag> taggedUsers) {
}
