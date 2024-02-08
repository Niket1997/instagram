package org.instagram.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.Filter;
import org.instagram.enums.AffinityState;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity(name = "user_affinities")
@Filter(name = "soft_deleted_filter", condition = "deleted_at IS NULL")
@IdClass(UserAffinityCompositePrimaryKey.class)
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"source", "destination", "state"})})
public class UserAffinity extends BaseEntity {
    @Id
    Long source;

    Long destination;

    @Id
    Long position;

    @Id
    AffinityState state;
}

