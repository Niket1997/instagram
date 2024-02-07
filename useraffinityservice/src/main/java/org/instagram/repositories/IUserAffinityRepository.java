package org.instagram.repositories;

import org.instagram.entities.UserAffinity;
import org.instagram.entities.UserAffinityCompositePrimaryKey;
import org.instagram.enums.AffinityState;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface IUserAffinityRepository extends CrudRepository<UserAffinity, UserAffinityCompositePrimaryKey> {
    List<UserAffinity> findAllBySourceAndStateOrderByPositionDesc(Long source, AffinityState state);

    UserAffinity findBySourceAndDestinationAndState(Long source, Long destination, AffinityState state);
}
