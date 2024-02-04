
package com.rumorify.ws.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rumorify.ws.model.User;
import com.rumorify.ws.service.UserService;

import lombok.AllArgsConstructor;

/**
 * @author tokay
 *
 */
@RestController
@RequestMapping("/api/v1/users")
@AllArgsConstructor
public class UserController {

	private UserService userService;

	@PostMapping(value = "/addUser")
	public void createUser(@RequestBody User user) {
		userService.save(user);
	}

}
