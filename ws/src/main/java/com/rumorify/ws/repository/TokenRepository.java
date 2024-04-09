package com.rumorify.ws.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.rumorify.ws.model.Token;

import jakarta.transaction.Transactional;

public interface TokenRepository extends JpaRepository<Token, String> {
    @Modifying
    @Transactional
    @Query("delete from Token t where t.user.id =:userId")
    public void deleteAllByUserId(int userId);
}
