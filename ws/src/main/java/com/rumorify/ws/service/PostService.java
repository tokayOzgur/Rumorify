package com.rumorify.ws.service;

import java.util.List;

import org.springframework.data.domain.Pageable;

import com.rumorify.ws.dto.requests.CreatePostRequest;
import com.rumorify.ws.dto.requests.UpdatePostRequest;
import com.rumorify.ws.dto.responses.GetAllPostResponse;
import com.rumorify.ws.dto.responses.GetPostByIdResponse;

public interface PostService {

        public void save(CreatePostRequest post, int currentUserId);

        public void updateByPostId(Long id, UpdatePostRequest entity);

        public void deleteByPostId(Long id);

        public List<GetAllPostResponse> findAll(Pageable pageable);

        public GetPostByIdResponse findById(Long id);

        public List<GetAllPostResponse> findByUserId(Pageable pageable, int userId);

        public List<GetAllPostResponse> findByUserIds(Pageable pageable, List<Integer> userIds);

        public List<GetAllPostResponse> findByIsDeletedFalse(Pageable pageable);
}
