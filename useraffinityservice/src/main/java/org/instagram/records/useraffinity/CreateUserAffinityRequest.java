package org.instagram.records.useraffinity;

import org.instagram.enums.AffinityState;

public record CreateUserAffinityRequest(Long sourceUserId, Long destinationUserId, AffinityState state) {
}
