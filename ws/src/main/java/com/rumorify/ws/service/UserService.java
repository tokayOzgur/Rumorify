
package com.rumorify.ws.service;

import java.util.List;

import com.rumorify.ws.dto.request.CreateUserRequest;
import com.rumorify.ws.dto.responses.GetAllActiveUsersResponse;
import com.rumorify.ws.dto.responses.GetAllUserResponse;
import com.rumorify.ws.dto.responses.GetUserByUserNameResponse;
import com.rumorify.ws.model.User;

/**
 * @author tokay
 */
public interface UserService {

	public GetUserByUserNameResponse findByUsername(String username);

	public void save(CreateUserRequest user);

	public void updateByUsername(User user);

	public void deleteByUsername(String username);

	public void activateUser(String token);

	public List<GetAllActiveUsersResponse> findAllByActive(boolean active);

	public List<GetAllUserResponse> findAll();
}
