
package com.rumorify.ws.service;

import com.rumorify.ws.model.User;

/**
 * @author tokay
 */
public interface UserService {

	public User findByUsername(String username);

	public User save(User user);

}
