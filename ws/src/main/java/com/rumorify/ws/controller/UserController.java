
package com.rumorify.ws.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rumorify.ws.model.User;

/**
 * @author tokay
 *
 */
@RestController
@RequestMapping("/api/v1/users")
public class UserController {

	@PostMapping(value = "/addUser")
	public void createUser(@RequestBody User user) {
		System.out.println(user);

	}

}
