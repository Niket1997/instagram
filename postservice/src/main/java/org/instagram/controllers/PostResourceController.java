package org.instagram.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.instagram.interfaces.postresource.IPostResourceService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/postresources")
@Slf4j
@RequiredArgsConstructor
public class PostResourceController {
    private final IPostResourceService postResourceService;

    @DeleteMapping("/{postResourceId}")
    public void deletePostResource(@PathVariable("postResourceId") Long postResourceId) {
        postResourceService.deletePostResourceById(postResourceId);
    }
}
