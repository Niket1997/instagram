package org.instagram.controllers;

import org.instagram.interfaces.IResourceUploadService;
import org.instagram.records.resourceupload.PrepareResourceUploadRequest;
import org.instagram.records.resourceupload.PrepareResourceUploadResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/resources/upload")
public class ResourceUploadController {
    private final IResourceUploadService resourceUploadService;

    public ResourceUploadController(IResourceUploadService resourceUploadService) {
        this.resourceUploadService = resourceUploadService;
    }

    @PostMapping("/prepare")
    public PrepareResourceUploadResponse prepareUpload(@RequestBody PrepareResourceUploadRequest request) {
        return resourceUploadService.prepareResourceUpload(request);
    }
}
