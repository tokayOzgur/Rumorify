
package com.rumorify.ws.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.rumorify.ws.model.User;
import com.rumorify.ws.repository.UserRepository;

import lombok.AllArgsConstructor;

/**
 * @author tokay
 *
 */
@Service
public class UserService {

	@Autowired
	private  UserRepository userRepository;

	private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

	public User findByUsername(String username) {
		return userRepository.findByUsername(username);
	}

	public User save(User user) {
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		return userRepository.save(user);
	}

}
