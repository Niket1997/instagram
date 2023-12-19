package org.instagram.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.Filter;
import org.instagram.enums.ResourceStatus;
import org.instagram.enums.ResourceType;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity(name = "resources")
@Filter(name = "soft_deleted_filter", condition = "deleted_at IS NULL")
public class Resource extends BaseEntity {
    @Id
    private Long id;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "resource_type")
    private ResourceType resourceType;

    @Column(name = "resource_status")
    private ResourceStatus resourceStatus;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public ResourceType getResourceType() {
        return resourceType;
    }

    public void setResourceType(ResourceType resourceType) {
        this.resourceType = resourceType;
    }

    public ResourceStatus getResourceStatus() {
        return resourceStatus;
    }

    public void setResourceStatus(ResourceStatus resourceStatus) {
        this.resourceStatus = resourceStatus;
    }
}
