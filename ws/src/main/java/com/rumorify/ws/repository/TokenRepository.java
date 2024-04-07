package com.rumorify.ws.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rumorify.ws.model.Token;

public interface TokenRepository extends JpaRepository<Token, String> {

}
