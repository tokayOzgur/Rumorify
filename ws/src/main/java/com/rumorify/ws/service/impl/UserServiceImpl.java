package com.rumorify.ws.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.rumorify.ws.dto.request.CreateUserRequest;
import com.rumorify.ws.dto.request.responses.GetUserByUserNameResponse;
import com.rumorify.ws.model.User;
import com.rumorify.ws.repository.UserRepository;
import com.rumorify.ws.service.ModelMapperService;
import com.rumorify.ws.service.UserService;
import com.rumorify.ws.exception.ResourceNotFoundException;

/**
 * @author tokay
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ModelMapperService mapper;

    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    public GetUserByUserNameResponse findByUsername(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with username: " + username));
        return mapper.forResponse().map(user, GetUserByUserNameResponse.class);
    }

    @Override
    public void save(CreateUserRequest userRequest) {
        userRequest.setPassword(passwordEncoder.encode(userRequest.getPassword()));
        User user = mapper.forRequest().map(userRequest, User.class);
        if (user == null)
            throw new ResourceNotFoundException("User not found with username: " + userRequest.getUsername());
        userRepository.save(user);
    }

    @Override
    public void updateByUsername(User user) {
        throw new UnsupportedOperationException("Unimplemented method 'updateByUsername'");
    }

    @Override
    public void deleteByUsername(String username) {
        throw new UnsupportedOperationException("Unimplemented method 'deleteByUsername'");
    }
}
