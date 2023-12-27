package org.instagram.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.Filter;
import org.instagram.enums.ParentType;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity(name = "likes")
@Filter(name = "soft_deleted_filter", condition = "deleted_at IS NULL")
@Table(
        indexes = {
                @Index(name = "idx_likes_parent_id_parent_type", columnList = "parent_id, parent_type"), // get all likes for a post/comment,
                @Index(name = "idx_likes_user_id", columnList = "user_id") // get all likes by a user,
        }
)
public class Like extends BaseEntity {
    @Id
    private Long id;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "parent_id")
    private Long parentId;

    @Column(name = "parent_type")
    private ParentType parentType;
}
