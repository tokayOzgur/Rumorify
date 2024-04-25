package com.rumorify.ws.service;

import com.rumorify.ws.dto.requests.CreatePostRequest;

public interface PostService {
        
        public void save(CreatePostRequest post, int id);
        
}
