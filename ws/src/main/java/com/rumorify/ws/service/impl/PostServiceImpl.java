package com.rumorify.ws.service.impl;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.rumorify.ws.dto.requests.CreatePostRequest;
import com.rumorify.ws.dto.requests.UpdatePostRequest;
import com.rumorify.ws.dto.responses.GetAllPostResponse;
import com.rumorify.ws.dto.responses.GetPostByIdResponse;
import com.rumorify.ws.exception.ResourceNotFoundException;
import com.rumorify.ws.exception.UserNotFoundException;
import com.rumorify.ws.file.FileService;
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
    private final FileService fileService;
    private final ModelMapperService mapper;
    private static final Logger logger = LogManager.getLogger(PostServiceImpl.class);

    @Override
    public void save(CreatePostRequest post, int currentUserId) {
        User user = userRepository.findById(currentUserId)
                .orElseThrow(() -> {
                    logger.error("User not found with id: {}", currentUserId);
                    return new UserNotFoundException(currentUserId);
                });
        Post newPost = mapper.forRequest().map(post, Post.class);
        newPost.setUser(user);
        postRepository.save(newPost);
    }

    @Override
    public void updateByPostId(Long id, UpdatePostRequest entity) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("Post not found with id: {}", id);
                    return new ResourceNotFoundException("rumorify.post.notfound.error.message");
                });
        if (entity.getContent() != null) post.setContent(entity.getContent());
        if (entity.getImageUrl() != null) post.setImageUrl(entity.getImageUrl());
        if (entity.getVideoUrl() != null) post.setVideoUrl(entity.getVideoUrl());
        postRepository.save(post);
    }

    @Override
    public void deleteByPostId(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("Post not found with id: {}", id);
                    return new ResourceNotFoundException("rumorify.post.notfound.error.message");
                });
        post.setDeleted(true);
        fileService.deleteFile(post.getImageUrl());
        fileService.deleteFile(post.getVideoUrl());
        postRepository.save(post);
    }

    @Override
    public List<GetAllPostResponse> findAll(Pageable pageable) {
        return postRepository.findAll(pageable).stream()
                .map(post -> this.mapper.forResponse()
                .map(post, GetAllPostResponse.class)).toList();
    }

    @Override
    public GetPostByIdResponse findById(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("Post not found with id: {}", id);
                    throw new ResourceNotFoundException("rumorify.post.notfound.error.message");
                });
        return mapper.forResponse().map(post, GetPostByIdResponse.class);
    }

    @Override
    public List<GetAllPostResponse> findByUserId(Pageable pageable ,int userId) {
        userRepository.findById(userId).orElseThrow(() -> {
            logger.error("User not found with id: {}", userId);
            throw new UserNotFoundException(userId);
        });
        return postRepository.findByUserId(userId, pageable).stream()
                .map(post -> this.mapper.forResponse().map(post, GetAllPostResponse.class)).toList();
    }

    @Override
    public List<GetAllPostResponse> findByUserIds(Pageable pageable, List<Integer> userIds) {
        List<User> users = userRepository.findByIdIn(userIds);
        List<Integer> ids = users.stream().map(User::getId).toList();
        return postRepository.findByUserIdIn(ids, pageable).stream()
                .map(post -> this.mapper.forResponse().map(post, GetAllPostResponse.class)).toList();
    }

    @Override
    public List<GetAllPostResponse> findByIsDeletedFalse(Pageable pageable) {
        return postRepository.findAllByIsDeletedFalse(pageable).stream()
                .map(post -> this.mapper.forResponse().map(post, GetAllPostResponse.class)).toList();
    }

}
