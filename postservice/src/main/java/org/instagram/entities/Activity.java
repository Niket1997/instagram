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
    private Long parentId; // get all likes & comments on a post/comment, this id will always be unique

    private Long numLikes;

    private Long numComments;

    public Long getNumberOfLikes() {
        return this.getNumLikes() == null ? 0L : this.getNumLikes();
    }

    public Long getNumberOfComments() {
        return this.getNumComments() == null ? 0L : this.getNumComments();
    }
}
