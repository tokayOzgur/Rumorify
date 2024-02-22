
package com.rumorify.ws.controller;

import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rumorify.ws.dto.request.CreateUserRequest;
import com.rumorify.ws.service.UserService;
import com.rumorify.ws.shared.GenericMessage;
import com.rumorify.ws.shared.Messages;

import lombok.AllArgsConstructor;

/**
 * @author tokay
 *
 */
@RestController
@RequestMapping("/api/v1/users")
@AllArgsConstructor
public class UserController {

	private final UserService userService;

	@PostMapping(value = "/addUser")
	public GenericMessage createUser(@RequestBody CreateUserRequest userRequest) {
		userService.save(userRequest);
		return new GenericMessage(Messages.getMessageForLocale("rumorify.create.user.success.message",
				LocaleContextHolder.getLocale()));
	}

	@PatchMapping(value = "/{token}/active")
	public GenericMessage activateUser(@PathVariable String token) {
		userService.activateUser(token);
		return new GenericMessage(Messages.getMessageForLocale("rumorify.activate.user.success.message",
				LocaleContextHolder.getLocale()));
	}

}
