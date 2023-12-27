package org.instagram.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.Filter;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity(name = "posts")
@Filter(name = "soft_deleted_filter", condition = "deleted_at IS NULL")
public class Post extends BaseEntity {
    @Id
    private Long id;

    @Column(name = "user_id")
    private Long userId;

    private String caption;
}
