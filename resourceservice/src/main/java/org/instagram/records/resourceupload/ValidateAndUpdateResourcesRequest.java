package org.instagram.records.resourceupload;

import java.util.List;

public record ValidateAndUpdateResourcesRequest(Long userId, List<Long> resourceIds) {
}
