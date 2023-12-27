package org.instagram.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.Filter;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity(name = "post_resources")
@Filter(name = "soft_deleted_filter", condition = "deleted_at IS NULL")
@Table(
        indexes = {
                @Index(name = "idx_post_resources_post_id", columnList = "post_id") // get all resourceIds for a post
        }
)
public class PostResource extends BaseEntity {
    @Id
    private Long id;

    @Column(name = "post_id")
    private Long postId;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "resource_id")
    private Long resourceId;

    @Column(name = "sequence_number")
    private Integer sequenceNumber;
}
