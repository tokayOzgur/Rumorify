package com.rumorify.ws.controller;

import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rumorify.ws.dto.requests.CreatePostRequest;
import com.rumorify.ws.service.AuthService;
import com.rumorify.ws.service.PostService;
import com.rumorify.ws.shared.GenericMessage;
import com.rumorify.ws.shared.Messages;

@RestController
@RequestMapping("/api/v1/posts")
public class PostController {
    private final PostService postService;
    private final AuthService authService;

    public PostController(PostService postService, AuthService authService) {
        this.postService = postService;
        this.authService = authService;
    }

    @PostMapping
    public GenericMessage createPost(@RequestBody CreatePostRequest postRequest) {
        postService.save(postRequest, authService.getCurrentUserId());
        return new GenericMessage(Messages.getMessageForLocale("rumorify.create.post.success.message",
                LocaleContextHolder.getLocale()));
    }

}
