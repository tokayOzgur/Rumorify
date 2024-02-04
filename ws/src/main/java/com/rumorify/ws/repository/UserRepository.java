
package com.rumorify.ws.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rumorify.ws.model.User;

/**
 * @author tokay
 *
 */
public interface UserRepository extends JpaRepository<User, Integer> {

	/**
	 * @param username
	 * @return
	 */
	User findByUsername(String username);

}
