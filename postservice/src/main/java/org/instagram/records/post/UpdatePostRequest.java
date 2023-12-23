package org.instagram.records.post;

import java.util.List;

public record UpdatePostRequest(String caption, List<Long> resourceIds, List<Long> taggedUserIds) {
}
