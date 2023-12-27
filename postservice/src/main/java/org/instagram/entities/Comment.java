package org.instagram.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.Filter;
import org.instagram.enums.ParentType;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity(name = "comments")
@Filter(name = "soft_deleted_filter", condition = "deleted_at IS NULL")
@Table(
        indexes = {
                @Index(name = "idx_comments_parent_id_parent_type", columnList = "parent_id, parent_type"), // get all comments for a post/comment
                @Index(name = "idx_comments_user_id", columnList = "user_id") // get all comments by a user
        }
)
// TODO: create entity tag
public class Comment extends BaseEntity {
    @Id
    private Long id;

    private String text;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "parent_id")
    private Long parentId;

    @Column(name = "parent_type")
    private ParentType parentType;
}
