package org.instagram.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.Filter;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity(name = "activities")
@Filter(name = "soft_deleted_filter", condition = "deleted_at IS NULL")
public class Activity extends BaseEntity {
    @Id
    private Long parentId; // get all likes & comments on a post/comment

    private Long likes;

    private Long comments;
}
