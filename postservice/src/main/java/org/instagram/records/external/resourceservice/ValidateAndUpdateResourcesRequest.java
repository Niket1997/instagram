package org.instagram.records.external.resourceservice;

import java.util.List;

public record ValidateAndUpdateResourcesRequest(Long userId, List<Long> resourceIds) {
}
