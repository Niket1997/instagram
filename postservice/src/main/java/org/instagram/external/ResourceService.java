package org.instagram.external;

import org.instagram.records.external.resourceservice.ValidateAndUpdateResourcesRequest;
import org.instagram.records.external.resourceservice.ValidateAndUpdateResourcesResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PutMapping;

@FeignClient(value = "resourceService", url = "${external.resource-service.base-url}")
public interface ResourceService {

    @PutMapping("/v1/resources")
    ValidateAndUpdateResourcesResponse validateAndUpdateResources(ValidateAndUpdateResourcesRequest request);
}
