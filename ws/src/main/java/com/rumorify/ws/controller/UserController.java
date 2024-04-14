
package com.rumorify.ws.controller;

import java.util.List;

import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rumorify.ws.config.CurrentUser;
import com.rumorify.ws.dto.requests.CreateUserRequest;
import com.rumorify.ws.dto.requests.PasswordResetRequest;
import com.rumorify.ws.dto.requests.UpdateUserRequest;
import com.rumorify.ws.dto.responses.GetAllActiveUsersResponse;
import com.rumorify.ws.dto.responses.GetAllUserResponse;
import com.rumorify.ws.dto.responses.GetUserByIdResponse;
import com.rumorify.ws.service.UserService;
import com.rumorify.ws.shared.GenericMessage;
import com.rumorify.ws.shared.Messages;

import jakarta.validation.Valid;
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
			@AuthenticationPrincipal CurrentUser currentUser) {
		return userService.findAllByActive(pageable, currentUser.getId());
	}

	@GetMapping(value = "/{id}")
	public GetUserByIdResponse findById(@PathVariable int id) {
		return userService.findById(id);
	}

	@PutMapping("/{id}")
	@PreAuthorize("#id == principal.id")
	public GenericMessage updateUserById(@PathVariable int id, @Valid @RequestBody UpdateUserRequest entity) {
		userService.updateByUserId(id, entity);
		return new GenericMessage(Messages.getMessageForLocale("rumorify.update.user.success.message",
				LocaleContextHolder.getLocale()));
	}

	@DeleteMapping("/{id}")
	@PreAuthorize("#id == principal.id")
	public GenericMessage deleteUserById(@PathVariable int id) {
		userService.deleteByUserId(id);
		return new GenericMessage(Messages.getMessageForLocale("rumorify.delete.user.success.message",
				LocaleContextHolder.getLocale()));
	}

	@PostMapping("/password-reset")
	public GenericMessage passwordResetRequest(@Valid @RequestBody PasswordResetRequest passwordResetRequest) {
		userService.resetPassword(passwordResetRequest);
		return new GenericMessage(Messages.getMessageForLocale("rumorify.reset.password.success.message",
				LocaleContextHolder.getLocale()));
	}
}
