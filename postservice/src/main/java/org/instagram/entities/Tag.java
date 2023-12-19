package org.instagram.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.Filter;
import org.instagram.enums.ParentType;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity(name = "tags")
@Filter(name = "soft_deleted_filter", condition = "deleted_at IS NULL")
@Table(
        indexes = {
                @Index(name = "idx_tags_user_id", columnList = "user_id"), // get all the posts/comments where a user is tagged
                @Index(name = "idx_tags_parent_id_parent_type", columnList = "parent_id, parent_type"), // get all tags on a certain post/comment
        }
)
public class Tag extends BaseEntity {
    @Id
    private Long id;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "parent_id")
    private Long parentId;

    @Column(name = "parent_type")
    private ParentType parentType;
}
