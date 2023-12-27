package org.instagram.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.instagram.entities.Activity;
import org.instagram.enums.ActivityType;
import org.instagram.enums.ActivityUpdateType;
import org.instagram.exceptions.activity.ActivityNotFoundException;
import org.instagram.interfaces.activity.IActivityService;
import org.instagram.repositories.IActivityRepository;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class ActivityService implements IActivityService {
    private final IActivityRepository activityRepository;

    @Override
    public Activity createActivity(Long parentId) {
        Activity activity = new Activity();
        activity.setParentId(parentId);
        return activityRepository.save(activity);
    }

    @Override
    public void updateActivity(Long parentId, ActivityType activityType, ActivityUpdateType activityUpdateType) {
        Activity activity = activityRepository.findByParentId(parentId);
        if (activity == null) {
            throw new ActivityNotFoundException("Activity not found for parentId: " + parentId); // custom exception (not found)
        }

        updateActivityCount(activity, activityType, activityUpdateType);

        activityRepository.save(activity);
    }

    @Override
    public Activity getActivity(Long parentId) {
        return activityRepository.findById(parentId).orElse(null);
    }

    private void updateActivityCount(Activity activity, ActivityType activityType, ActivityUpdateType activityUpdateType) {
        int activityUpdateValue = activityUpdateType == ActivityUpdateType.INCREMENT ? 1 : -1;
        switch (activityType) {
            case LIKE:
                activity.setNumLikes(activity.getNumberOfLikes() + activityUpdateValue);
                break;
            case COMMENT:
                activity.setNumComments(activity.getNumberOfComments() + activityUpdateValue);
                break;
            default:
                throw new IllegalArgumentException("Invalid activity type"); // should never happen, but just in case...
        }
    }
}
