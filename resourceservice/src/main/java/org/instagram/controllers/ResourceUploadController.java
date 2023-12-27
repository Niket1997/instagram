package org.instagram.controllers;

import lombok.extern.slf4j.Slf4j;
import org.instagram.interfaces.IResourceUploadService;
import org.instagram.records.resourceupload.PrepareResourceUploadRequest;
import org.instagram.records.resourceupload.PrepareResourceUploadResponse;
import org.instagram.records.resourceupload.ValidateAndUpdateResourcesRequest;
import org.instagram.records.resourceupload.ValidateAndUpdateResourcesResponse;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/resources")
@Slf4j
public class ResourceUploadController {
    private final IResourceUploadService resourceUploadService;

    public ResourceUploadController(IResourceUploadService resourceUploadService) {
        this.resourceUploadService = resourceUploadService;
    }

    @PostMapping("/upload/prepare")
    public PrepareResourceUploadResponse prepareUpload(@RequestBody PrepareResourceUploadRequest request) {
        log.info("logging request");
        return resourceUploadService.prepareResourceUpload(request);
    }

    @PutMapping
    public ValidateAndUpdateResourcesResponse validateAndUpdateResources(@RequestBody ValidateAndUpdateResourcesRequest request) {
        return resourceUploadService.validateAndUpdateResources(request);
    }
}
