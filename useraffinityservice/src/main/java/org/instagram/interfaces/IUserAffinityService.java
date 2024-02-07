package org.instagram.interfaces;

import org.instagram.entities.UserAffinity;
import org.instagram.enums.AffinityState;
import org.instagram.records.useraffinity.CreateUserAffinityRequest;

import java.util.List;

public interface IUserAffinityService {
    UserAffinity createUserAffinity(CreateUserAffinityRequest request);

    List<UserAffinity> getUserAffinityByState(Long userId, AffinityState state);

    void deleteUserAffinity(Long sourceUserId, Long destinationUserId, AffinityState state);
}
