
package com.rumorify.ws.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rumorify.ws.model.User;

/**
 * @author tokay
 *
 */
public interface UserRepository extends JpaRepository<User, Integer> {

	Optional<User> findByUsername(String username);

	User findByEmail(String email);

}
