package com.rumorify.ws.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.rumorify.ws.model.Post;

public interface PostRepository extends JpaRepository<Post, Long> {

    Page<Post> findAllByUser_IdAndIsDeletedFalse(Long userId, Pageable pageable);

    Optional<Post> findByIdAndIsDeletedFalse(Long id);

    Page<Post> findAllByIsDeletedFalse(Pageable pageable);

    Page<Post> findAllByIsDeletedFalseAndContentContainingIgnoreCase(String content, Pageable pageable);
}
