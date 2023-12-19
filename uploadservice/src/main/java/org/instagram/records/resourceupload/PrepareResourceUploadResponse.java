package org.instagram.records.resourceupload;

import org.instagram.enums.ResourceStatus;
import org.instagram.enums.ResourceType;

public record PrepareResourceUploadResponse(Long resourceId, Long userId, ResourceType resourceType,
                                            ResourceStatus resourceStatus, String preSignedUrl) {
}
