
package com.rumorify.ws.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.rumorify.ws.dto.requests.CreateUserRequest;
import com.rumorify.ws.dto.responses.GetAllActiveUsersResponse;
import com.rumorify.ws.dto.responses.GetAllUserResponse;
import com.rumorify.ws.dto.responses.GetUserByIdResponse;
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

	public Page<GetAllActiveUsersResponse> findAllByActive(Pageable pageable);

	public List<GetAllUserResponse> findAll();

	public GetUserByIdResponse findById(int id);
}
