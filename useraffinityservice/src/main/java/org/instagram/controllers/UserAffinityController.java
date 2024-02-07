package org.instagram.controllers;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.instagram.entities.UserAffinity;
import org.instagram.enums.AffinityState;
import org.instagram.interfaces.IUserAffinityService;
import org.instagram.records.useraffinity.CreateUserAffinityRequest;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/users/affinity")
@Slf4j
@AllArgsConstructor
public class UserAffinityController {
    public final IUserAffinityService userAffinityService;

    @PostMapping
    public UserAffinity createUserAffinity(@RequestBody CreateUserAffinityRequest request) {
        log.info("received request to create user affinity" + request);
        return userAffinityService.createUserAffinity(request);
    }

    @GetMapping("/{userId}/{state}")
    public List<UserAffinity> getUserAffinityByState(@PathVariable("state") AffinityState state, @PathVariable("userId") Long userId) {
        log.info("received request to get user affinities for userId: " + userId + " & state: " + state);
        return userAffinityService.getUserAffinityByState(userId, state);
    }

    @DeleteMapping("/{sourceUserId}/{destinationUserId}/{state}")
    public void deleteUserAffinity(@PathVariable("sourceUserId") Long sourceUserId, @PathVariable("destinationUserId") Long destinationUserId, @PathVariable("state") AffinityState state) {
        log.info("received request to delete user affinity for sourceUserId: " + sourceUserId + " &  destinationUserId: " + destinationUserId + " & state: " + state);
        userAffinityService.deleteUserAffinity(sourceUserId, destinationUserId, state);
    }
}
