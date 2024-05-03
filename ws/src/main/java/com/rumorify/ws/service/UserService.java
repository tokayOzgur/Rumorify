
package com.rumorify.ws.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.rumorify.ws.dto.requests.CreateUserRequest;
import com.rumorify.ws.dto.requests.PasswordResetRequest;
import com.rumorify.ws.dto.requests.UpdatePasswordRequest;
import com.rumorify.ws.dto.requests.UpdateUserRequest;
import com.rumorify.ws.dto.responses.GetAllActiveUsersResponse;
import com.rumorify.ws.dto.responses.GetAllUserResponse;
import com.rumorify.ws.dto.responses.GetUserByEmailResponse;
import com.rumorify.ws.dto.responses.GetUserByIdResponse;
import com.rumorify.ws.dto.responses.GetUserByUserNameResponse;

/**
 * @author tokay
 */
public interface UserService {

	public GetUserByUserNameResponse findByUsername(String username);

	public void save(CreateUserRequest user);

	public void updateByUserId(int id, UpdateUserRequest entity);

	public void deleteByUserId(int id);

	public void activateUser(String token);

	public Page<GetAllActiveUsersResponse> findAllByActive(Pageable pageable, int id);

	public Page<GetAllUserResponse> findAll(Pageable pageable);

	public GetUserByIdResponse findById(int id);

	public GetUserByEmailResponse findByEmail(String email);

	public void resetPassword(PasswordResetRequest email);

	public void updatePassword(String token, UpdatePasswordRequest request);
}
