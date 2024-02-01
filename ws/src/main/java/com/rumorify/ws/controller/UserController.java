
package com.rumorify.ws.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;


/**
 * @author tokay
 *
 */
@RestController
@CrossOrigin("http://localhost:5173/")
@RequestMapping("/api/v1/users")
public class UserController {
	
	@PostMapping(value="/addUser")
	public SomeEnityData createUser(@RequestBody SomeEnityData entity) {
		//TODO: process POST request
		
		return entity;
	}
	

}
