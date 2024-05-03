package com.rumorify.ws.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.rumorify.ws.dto.requests.CreatePostRequest;
import com.rumorify.ws.dto.requests.UpdatePostRequest;
import com.rumorify.ws.dto.responses.GetAllActivePostResponse;
import com.rumorify.ws.dto.responses.GetAllPostByUserIdResponse;
import com.rumorify.ws.dto.responses.GetAllPostResponse;
import com.rumorify.ws.dto.responses.GetPostByIdResponse;

public interface PostService {

        public void save(CreatePostRequest post, int currentUserId);

        public void updateByPostId(Long id, UpdatePostRequest entity);

        public void deleteByPostId(Long id);

        public Page<GetAllPostResponse> findAll(Pageable pageable);

        public GetPostByIdResponse findById(Long id);

        public Page<GetAllPostByUserIdResponse> findByUserId(Pageable pageable, int userId);

        public Page<GetAllPostByUserIdResponse> findByUserIds(Pageable pageable, List<Integer> userIds);

        public Page<GetAllActivePostResponse> findByIsDeletedFalse(Pageable pageable);
}
