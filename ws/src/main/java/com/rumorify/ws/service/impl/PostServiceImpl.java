package com.rumorify.ws.service.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import com.rumorify.ws.dto.requests.CreatePostRequest;
import com.rumorify.ws.exception.UserNotFoundException;
import com.rumorify.ws.model.Post;
import com.rumorify.ws.model.User;
import com.rumorify.ws.repository.PostRepository;
import com.rumorify.ws.repository.UserRepository;
import com.rumorify.ws.service.ModelMapperService;
import com.rumorify.ws.service.PostService;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
@Service
public class PostServiceImpl implements PostService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final ModelMapperService mapper;
    private static final Logger logger = LogManager.getLogger(PostServiceImpl.class);

    @Override
    public void save(CreatePostRequest post, int id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("User not found with id: {}", id);
                    return new UserNotFoundException(id);
                });
        Post newPost = mapper.forRequest().map(post, Post.class);
        newPost.setUser(user);
        postRepository.save(newPost);
    }

}
