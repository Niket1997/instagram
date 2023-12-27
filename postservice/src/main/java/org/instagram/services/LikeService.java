package org.instagram.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.instagram.entities.Like;
import org.instagram.enums.ActivityType;
import org.instagram.enums.ActivityUpdateType;
import org.instagram.enums.ParentType;
import org.instagram.exceptions.like.DuplicateLikeException;
import org.instagram.exceptions.like.ParentNotFoundException;
import org.instagram.interfaces.activity.IActivityService;
import org.instagram.interfaces.comment.ICommentService;
import org.instagram.interfaces.like.ILikeService;
import org.instagram.interfaces.post.IPostService;
import org.instagram.records.like.UpdateLikeRequest;
import org.instagram.repositories.ILikeRepository;
import org.instagram.utils.Snowflake;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class LikeService implements ILikeService {
    public final ILikeRepository likeRepository;
    public final IActivityService activityService;
    public final IPostService postService;
    public final ICommentService commentService;
    public final Snowflake snowflake;

    private boolean checkIfParentExists(Long parentId, ParentType parentType) {
        switch (parentType) {
            case POST: {
                try {
                    return postService.getPostById(parentId) != null;
                } catch (Exception e) {
                    log.error(e.getMessage());
                    return true;
                }
            }
            case COMMENT: {
                try {
                    return commentService.getCommentById(parentId) != null;
                } catch (Exception e) {
                    log.error(e.getMessage());
                    return true;
                }
            }
            default:
                return true; // should never happen, but just in case...
        }
    }

    @Override
    public void like(UpdateLikeRequest request) {
        // validate request -> parent is valid
        if (!checkIfParentExists(request.parentId(), request.parentType())) {
            throw new ParentNotFoundException("Parent with id " + request.parentId() + " and type " + request.parentType() + " does not exist");
        }
        // validate request if not already liked
        Like like = likeRepository.findByParentIdAndParentTypeAndUserId(request.parentId(), request.parentType(), request.userId());
        if (like != null) {
            throw new DuplicateLikeException("User with id " + request.userId() + " has already liked parent with id " + request.parentId() + " and type " + request.parentType() + " before");
        }

        // create or update activity
        activityService.updateActivity(request.parentId(), ActivityType.LIKE, ActivityUpdateType.INCREMENT);

        // create like
        Like newLike = new Like();
        newLike.setUserId(request.userId());
        newLike.setId(snowflake.nextId());
        newLike.setParentId(request.parentId());
        newLike.setParentType(request.parentType());

        // save like
        likeRepository.save(newLike);
    }

    @Override
    public void unlike(UpdateLikeRequest request) {
        // validate request -> parent is valid
        if (!checkIfParentExists(request.parentId(), request.parentType())) {
            throw new ParentNotFoundException("Parent with id " + request.parentId() + " and type " + request.parentType() + " does not exist");
        }

        // validate request if not already liked
        Like like = likeRepository.findByParentIdAndParentTypeAndUserId(request.parentId(), request.parentType(), request.userId());
        if (like == null) {
            throw new ParentNotFoundException("User with id " + request.userId() + " has not liked parent with id " + request.parentId() + " and type " + request.parentType() + " before");
        }

        // create or update activity
        activityService.updateActivity(request.parentId(), ActivityType.LIKE, ActivityUpdateType.DECREMENT);
        likeRepository.delete(like);
    }

    @Override
    public List<Like> getLikes(Long parentId, ParentType parentType) {
        return likeRepository.findByParentIdAndParentTypeOrderByCreatedAtDesc(parentId, parentType);
    }
}
