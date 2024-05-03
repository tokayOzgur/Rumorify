package com.rumorify.ws.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.rumorify.ws.model.Post;

public interface PostRepository extends JpaRepository<Post, Long> {

    Optional<Post> findByIdAndIsDeleted(Long id, boolean isDeleted);

    Page<Post> findAllByIsDeleted(boolean isDeleted, Pageable pageable);

    Page<Post> findByUserIdAndIsDeleted(int userId, boolean isDeleted, Pageable pageable);

    Page<Post> findByUserIdInAndIsDeleted(List<Integer> userIds, boolean isDeleted, Pageable pageable);

}