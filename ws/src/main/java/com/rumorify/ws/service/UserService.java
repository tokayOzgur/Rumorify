
package com.rumorify.ws.service;

import com.rumorify.ws.dto.request.CreateUserRequest;
import com.rumorify.ws.dto.request.responses.GetUserByUserNameResponse;
import com.rumorify.ws.model.User;

/**
 * @author tokay
 */
public interface UserService {

	public GetUserByUserNameResponse findByUsername(String username);

	public void save(CreateUserRequest user);

	public void updateByUsername(User user);

	public void deleteByUsername(String username);
}
