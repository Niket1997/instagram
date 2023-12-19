package org.instagram.interfaces;

import org.instagram.records.resourceupload.PrepareResourceUploadRequest;
import org.instagram.records.resourceupload.PrepareResourceUploadResponse;

public interface IResourceUploadService {
    PrepareResourceUploadResponse prepareResourceUpload(PrepareResourceUploadRequest request);
}
