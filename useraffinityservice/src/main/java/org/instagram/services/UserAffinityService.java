package org.instagram.services;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.instagram.entities.UserAffinity;
import org.instagram.enums.AffinityState;
import org.instagram.exceptions.useraffinity.UserAffinityNotFoundException;
import org.instagram.interfaces.IUserAffinityService;
import org.instagram.records.useraffinity.CreateUserAffinityRequest;
import org.instagram.repositories.IUserAffinityRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@AllArgsConstructor
@Service
public class UserAffinityService implements IUserAffinityService {
    public final IUserAffinityRepository userAffinityRepository;

    @Override
    public UserAffinity createUserAffinity(CreateUserAffinityRequest request) {
        UserAffinity userAffinity = new UserAffinity();
        userAffinity.setSource(request.sourceUserId());
        userAffinity.setDestination(request.destinationUserId());
        userAffinity.setPosition(System.currentTimeMillis());
        userAffinity.setState(request.state());
        return userAffinityRepository.save(userAffinity);
    }

    @Override
    public List<UserAffinity> getUserAffinityByState(Long userId, AffinityState state) {
        List<UserAffinity> userAffinityList = userAffinityRepository.findAllBySourceAndStateOrderByPositionDesc(userId, state);
        return userAffinityList.stream().filter(it -> it.getDeletedAt() == null).toList();
    }

    @Override
    public void deleteUserAffinity(Long sourceUserId, Long destinationUserId, AffinityState state) {
        UserAffinity userAffinity = userAffinityRepository.findBySourceAndDestinationAndState(sourceUserId, destinationUserId, state);
        if (userAffinity == null) {
            throw new UserAffinityNotFoundException("user affinity not found for sourceUserId: " + sourceUserId + " destinationUserId: " + destinationUserId + " & state: " + state);
        }
        userAffinity.markDeleted();
        userAffinityRepository.save(userAffinity);
    }
}
