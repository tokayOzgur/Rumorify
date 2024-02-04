
package com.rumorify.ws.service;

import org.springframework.stereotype.Service;

import com.rumorify.ws.model.User;
import com.rumorify.ws.repository.UserRepository;

import lombok.AllArgsConstructor;

/**
 * @author tokay
 *
 */
@Service
@AllArgsConstructor
public class UserService {

	private final UserRepository userRepository;
	
	public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
	
	public User save(User user) {
        return userRepository.save(user);
    }

}
