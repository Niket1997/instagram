package org.instagram.repositories;

import org.instagram.entities.UserAffinity;
import org.instagram.entities.UserAffinityCompositePrimaryKey;
import org.instagram.enums.AffinityState;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface IUserAffinityRepository extends CrudRepository<UserAffinity, UserAffinityCompositePrimaryKey> {
    @Query("SELECT ua FROM user_affinities ua WHERE ua.source = ?1 AND ua.state = ?2 AND ua.deletedAt IS NULL ORDER BY ua.position DESC")
    List<UserAffinity> findAllBySourceAndStateOrderByPositionDesc(Long source, AffinityState state);

    UserAffinity findBySourceAndDestinationAndState(Long source, Long destination, AffinityState state);
}
