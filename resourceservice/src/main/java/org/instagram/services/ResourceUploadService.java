package org.instagram.services;

import com.amazonaws.HttpMethod;
import com.amazonaws.services.s3.AmazonS3;
import org.instagram.entities.Resource;
import org.instagram.enums.ResourceStatus;
import org.instagram.exceptions.ResourceNotUploadedException;
import org.instagram.exceptions.UserIdMismatchException;
import org.instagram.interfaces.IResourceUploadService;
import org.instagram.records.resourceupload.PrepareResourceUploadRequest;
import org.instagram.records.resourceupload.PrepareResourceUploadResponse;
import org.instagram.records.resourceupload.ValidateAndUpdateResourcesRequest;
import org.instagram.records.resourceupload.ValidateAndUpdateResourcesResponse;
import org.instagram.repositories.IResourceRepository;
import org.instagram.utils.Snowflake;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ResourceUploadService implements IResourceUploadService {
    private final IResourceRepository resourceRepository;
    private final Snowflake snowflake;
    private final AmazonS3 s3Client;


    @Value("${aws.s3.bucket.name}")
    private String bucketName;

    public ResourceUploadService(IResourceRepository resourceRepository, Snowflake snowflake, AmazonS3 s3Client) {
        this.resourceRepository = resourceRepository;
        this.snowflake = snowflake;
        this.s3Client = s3Client;
    }

    @Override
    public PrepareResourceUploadResponse prepareResourceUpload(PrepareResourceUploadRequest request) {
        Long resourceId = snowflake.nextId();

        Resource resource = new Resource();
        resource.setId(resourceId);
        resource.setUserId(request.userId());
        resource.setResourceType(request.resourceType());

        String preSignedUrl = generatePreSignedUrl(request.userId(), resourceId);
        resource.setResourceStatus(ResourceStatus.PRESIGN_URL_GENERATED);

        resourceRepository.save(resource);

        return new PrepareResourceUploadResponse(resource.getId(), resource.getUserId(), resource.getResourceType(), resource.getResourceStatus(), preSignedUrl);

    }

    @Override
    public ValidateAndUpdateResourcesResponse validateAndUpdateResources(ValidateAndUpdateResourcesRequest request) {
        // fetch all resources with given resource ids
        Iterable<Resource> resources = resourceRepository.findAllById(request.resourceIds());

        // iterate over resources & see if the userId is same as in request
        List<Long> successfullyUploadedResourceIds = new ArrayList<>();
        for (Resource resource : resources) {
            if (!Objects.equals(resource.getUserId(), request.userId())) {
                throw new UserIdMismatchException("user id mismatch");
            }

            if (resource.getResourceStatus() == ResourceStatus.UPLOAD_SUCCESS) {
                successfullyUploadedResourceIds.add(resource.getId());
                continue;
            }

            if (!checkIfObjectExists(request.userId(), resource.getId())) {
                throw new ResourceNotUploadedException("resource with id: " + resource.getId() + " is not uploaded on blob storage");
            }
            resource.setResourceStatus(ResourceStatus.UPLOAD_SUCCESS);
            resourceRepository.save(resource);
            successfullyUploadedResourceIds.add(resource.getId());
        }

        request.resourceIds().removeAll(successfullyUploadedResourceIds);
        return new ValidateAndUpdateResourcesResponse(request.resourceIds().isEmpty(), request.resourceIds());
    }

    private boolean checkIfObjectExists(Long userId, Long resourceId) {
        String filePath = userId + "/" + resourceId;
        return s3Client.doesObjectExist(bucketName, filePath);
    }

    private String generatePreSignedUrl(Long userId, Long resourceId) {
        // TODO: Implement a factory pattern to talk to various cloud providers
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.MINUTE, 10);
        String filePath = userId + "/" + resourceId;
        return s3Client.generatePresignedUrl(bucketName, filePath, calendar.getTime(), HttpMethod.PUT).toString();
    }
}
