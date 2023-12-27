package org.instagram.interfaces.activity;

import org.instagram.entities.Activity;
import org.instagram.enums.ActivityType;
import org.instagram.enums.ActivityUpdateType;

public interface IActivityService {
    Activity createActivity(Long parentId);

    void updateActivity(Long parentId, ActivityType activityType, ActivityUpdateType activityUpdateType);

    Activity getActivity(Long parentId);
}
