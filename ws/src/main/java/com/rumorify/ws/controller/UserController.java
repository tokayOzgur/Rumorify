
package com.rumorify.ws.controller;

import java.util.List;

import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rumorify.ws.dto.requests.CreateUserRequest;
import com.rumorify.ws.dto.requests.UpdateUserRequest;
import com.rumorify.ws.dto.responses.GetAllActiveUsersResponse;
import com.rumorify.ws.dto.responses.GetAllUserResponse;
import com.rumorify.ws.dto.responses.GetUserByIdResponse;
import com.rumorify.ws.exception.AuthorizationException;
import com.rumorify.ws.service.TokenService;
import com.rumorify.ws.service.UserService;
import com.rumorify.ws.shared.GenericMessage;
import com.rumorify.ws.shared.Messages;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PutMapping;

/**
 * @author tokay
 *
 */
@RestController
@RequestMapping("/api/v1/users")
@AllArgsConstructor
public class UserController {

	private final UserService userService;
	private final TokenService tokenService;

	// TODO: edit the all paths to be more RESTful

	@PostMapping
	public GenericMessage createUser(@RequestBody CreateUserRequest userRequest) {
		userService.save(userRequest);
		return new GenericMessage(Messages.getMessageForLocale("rumorify.create.user.success.message",
				LocaleContextHolder.getLocale()));
	}

	@PatchMapping(value = "/activation/{token}")
	public GenericMessage activateUser(@PathVariable String token) {
		userService.activateUser(token);
		return new GenericMessage(Messages.getMessageForLocale("rumorify.activate.user.success.message",
				LocaleContextHolder.getLocale()));
	}

	@GetMapping(value = "/all")
	public List<GetAllUserResponse> findAllUser() {
		return userService.findAll();
	}

	@GetMapping
	public Page<GetAllActiveUsersResponse> findAllActiveUser(Pageable pageable,
			@RequestHeader(name = "Authorization", required = false) String authorizationHeader) {
		return userService.findAllByActive(pageable, tokenService.verifyToken(authorizationHeader));
	}

	@GetMapping(value = "/{id}")
	public GetUserByIdResponse findById(@PathVariable int id) {
		return userService.findById(id);
	}

	@PutMapping("/{id}")
	public GenericMessage updateUserById(@PathVariable int id, @RequestBody UpdateUserRequest entity,
			@RequestHeader(name = "Authorization", required = false) String authorizationHeader) {
		var loggedInUser = tokenService.verifyToken(authorizationHeader);
		if (loggedInUser == 0 && loggedInUser != id) {
			throw new AuthorizationException(Messages.getMessageForLocale("rumorify.authorization.failed.message",
					LocaleContextHolder.getLocale()));
		}
		userService.updateByUserId(id, entity);
		return new GenericMessage(Messages.getMessageForLocale("rumorify.update.user.success.message",
				LocaleContextHolder.getLocale()));

	}

}
