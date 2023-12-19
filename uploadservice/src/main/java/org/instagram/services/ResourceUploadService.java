package org.instagram.services;

import com.amazonaws.HttpMethod;
import com.amazonaws.services.s3.AmazonS3;
import lombok.extern.slf4j.Slf4j;
import org.instagram.entities.Resource;
import org.instagram.enums.ResourceStatus;
import org.instagram.interfaces.IResourceUploadService;
import org.instagram.records.resourceupload.PrepareResourceUploadRequest;
import org.instagram.records.resourceupload.PrepareResourceUploadResponse;
import org.instagram.repositories.IResourceRepository;
import org.instagram.utils.Snowflake;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;

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

    private String generatePreSignedUrl(Long userId, Long resourceId) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.MINUTE, 10);
        String filePath = userId + "/" + resourceId;
        return s3Client.generatePresignedUrl(bucketName, filePath, calendar.getTime(), HttpMethod.PUT).toString();
    }
}
