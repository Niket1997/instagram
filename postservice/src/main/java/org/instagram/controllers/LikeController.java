package org.instagram.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.instagram.entities.Like;
import org.instagram.enums.ParentType;
import org.instagram.interfaces.like.ILikeService;
import org.instagram.records.like.UpdateLikeRequest;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
public class LikeController {
    public final ILikeService likeService;

    @PostMapping("/v1/like")
    public void like(@RequestBody UpdateLikeRequest request) {
        likeService.like(request);
    }

    @PostMapping("/v1/unlike")
    public void unlike(@RequestBody UpdateLikeRequest request) {
        likeService.unlike(request);
    }

    @GetMapping("/v1/{parentType}/likes/{parentId}")
    public List<Like> getLikes(@PathVariable("parentId") Long parentId, @PathVariable("parentType") ParentType parentType) {
        return likeService.getLikes(parentId, parentType);
    }
}
