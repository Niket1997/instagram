package org.instagram.records.external.resourceservice;

import java.util.List;

public record ValidateAndUpdateResourcesResponse(boolean success, List<Long> failedResourceIds) {
}
