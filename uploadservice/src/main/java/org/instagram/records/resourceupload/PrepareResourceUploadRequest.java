package org.instagram.records.resourceupload;

import org.instagram.enums.ResourceType;

public record PrepareResourceUploadRequest(Long userId, ResourceType resourceType) {
}
