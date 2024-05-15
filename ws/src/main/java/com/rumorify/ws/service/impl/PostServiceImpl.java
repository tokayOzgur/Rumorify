package com.rumorify.ws.service.impl;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.rumorify.ws.dto.requests.CreatePostRequest;
import com.rumorify.ws.dto.requests.UpdatePostRequest;
import com.rumorify.ws.dto.responses.GetAllActivePostResponse;
import com.rumorify.ws.dto.responses.GetAllPostByUserIdResponse;
import com.rumorify.ws.dto.responses.GetAllPostResponse;
import com.rumorify.ws.dto.responses.GetPostByIdResponse;
import com.rumorify.ws.exception.AccessDeniedException;
import com.rumorify.ws.exception.ResourceNotFoundException;
import com.rumorify.ws.exception.UserNotFoundException;
import com.rumorify.ws.file.FileService;
import com.rumorify.ws.model.Post;
import com.rumorify.ws.model.User;
import com.rumorify.ws.repository.PostRepository;
import com.rumorify.ws.repository.UserRepository;
import com.rumorify.ws.service.AuthService;
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
    private final AuthService authService;
    private static final Logger logger = LogManager.getLogger(PostServiceImpl.class);

    @Override
    public void save(CreatePostRequest postRequest, int currentUserId) {
        User user = userRepository.findById(currentUserId)
                .orElseThrow(() -> {
                    logger.error("User not found with id: {}", currentUserId);
                    return new UserNotFoundException(currentUserId);
                });
        Post post = mapper.forRequest().map(postRequest, Post.class);
        if (postRequest.getImage() != null) {
            String fileName = fileService.saveBase4StringAsFile(postRequest.getImage(), "post");
            post.setImageUrl(fileName);
        }
        if (postRequest.getVideo() != null) {
            String fileName = fileService.saveBase4StringAsFile(postRequest.getVideo(), "post");
            post.setImageUrl(fileName);
        }
        post.setUser(user);
        postRepository.save(post);
    }

    @Override
    public void updateByPostId(Long id, UpdatePostRequest entity) {
        Post post = postRepository.findByIdAndIsDeleted(id, false)
                .orElseThrow(() -> {
                    logger.error("Post not found with id: {}", id);
                    return new ResourceNotFoundException("rumorify.post.notfound.error.message");
                });
        if (post.getUser().getId() != authService.getCurrentUserId()) {
            logger.error("User not authorized to update post with id: {}", id);
            throw new AccessDeniedException();
        }
        if (entity.getContent() != null)
            post.setContent(entity.getContent());
        if (entity.getImageUrl() != null)
            post.setImageUrl(entity.getImageUrl());
        if (entity.getVideoUrl() != null)
            post.setVideoUrl(entity.getVideoUrl());
        postRepository.save(post);
    }

    @Override
    public void deleteByPostId(Long id) {
        Post post = postRepository.findByIdAndIsDeleted(id, false)
                .orElseThrow(() -> {
                    logger.error("Post not found with id: {}", id);
                    return new ResourceNotFoundException("rumorify.post.notfound.error.message");
                });
        post.setDeleted(true);
        fileService.deleteFile(post.getImageUrl(), "post");
        fileService.deleteFile(post.getVideoUrl(), "post");
        postRepository.save(post);
    }

    @Override
    public Page<GetAllPostResponse> findAll(Pageable pageable) {
        Page<Post> pagePosts = postRepository.findAll(pageable);
        return pagePosts.map(post -> mapper.forResponse().map(post, GetAllPostResponse.class));
    }

    @Override
    public GetPostByIdResponse findById(Long id) {
        Post post = postRepository.findByIdAndIsDeleted(id, false)
                .orElseThrow(() -> {
                    logger.error("Post not found with id: {}", id);
                    throw new ResourceNotFoundException("rumorify.post.notfound.error.message");
                });
        return mapper.forResponse().map(post, GetPostByIdResponse.class);
    }

    @Override
    public Page<GetAllPostByUserIdResponse> findByUserId(Pageable pageable, int userId) {
        userRepository.findById(userId).orElseThrow(() -> {
            logger.error("User not found with id: {}", userId);
            throw new UserNotFoundException(userId);
        });
        Page<Post> pagePosts = postRepository.findByUserIdAndIsDeleted(userId, false, pageable);
        return pagePosts.map(post -> mapper.forResponse().map(post, GetAllPostByUserIdResponse.class));
    }

    @Override
    public Page<GetAllPostByUserIdResponse> findByUserIds(Pageable pageable, List<Integer> userIds) {
        List<User> users = userRepository.findByIdIn(userIds);
        List<Integer> ids = users.stream().map(User::getId).toList();
        return postRepository.findByUserIdInAndIsDeleted(ids, false, pageable)
                .map(post -> this.mapper.forResponse().map(post, GetAllPostByUserIdResponse.class));
    }

    @Override
    public Page<GetAllActivePostResponse> findByIsDeletedFalse(Pageable pageable) {
        Pageable sortedByCreationTimeDesc = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(),
                Sort.by("creationTime").descending());
        return postRepository.findAllByIsDeleted(false, sortedByCreationTimeDesc)
                .map(post -> this.mapper.forResponse().map(post, GetAllActivePostResponse.class));
    }

}
