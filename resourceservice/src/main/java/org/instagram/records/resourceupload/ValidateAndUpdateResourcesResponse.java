package org.instagram.records.resourceupload;

import java.util.List;

public record ValidateAndUpdateResourcesResponse(boolean success, List<Long> failedResourceIds) {
}
