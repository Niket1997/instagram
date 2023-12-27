package org.instagram.interfaces;

import org.instagram.records.resourceupload.PrepareResourceUploadRequest;
import org.instagram.records.resourceupload.PrepareResourceUploadResponse;
import org.instagram.records.resourceupload.ValidateAndUpdateResourcesRequest;
import org.instagram.records.resourceupload.ValidateAndUpdateResourcesResponse;
import org.springframework.web.bind.annotation.RequestBody;

public interface IResourceUploadService {
    PrepareResourceUploadResponse prepareResourceUpload(PrepareResourceUploadRequest request);
    ValidateAndUpdateResourcesResponse validateAndUpdateResources(ValidateAndUpdateResourcesRequest request);
}
