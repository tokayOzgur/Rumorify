package com.rumorify.ws.service.impl;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.mail.MailException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.rumorify.ws.dto.request.CreateUserRequest;
import com.rumorify.ws.dto.responses.GetUserByUserNameResponse;
import com.rumorify.ws.exception.ActivationNotificationException;
import com.rumorify.ws.exception.InvalidTokenException;
import com.rumorify.ws.exception.NotUniqueEmailException;
import com.rumorify.ws.exception.ResourceNotFoundException;
import com.rumorify.ws.model.User;
import com.rumorify.ws.repository.UserRepository;
import com.rumorify.ws.service.EmailService;
import com.rumorify.ws.service.ModelMapperService;
import com.rumorify.ws.service.UserService;

import jakarta.transaction.Transactional;

/**
 * @author tokay
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ModelMapperService mapper;
    @Autowired
    private EmailService emailService;

    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    public GetUserByUserNameResponse findByUsername(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException());
        return mapper.forResponse().map(user, GetUserByUserNameResponse.class);
    }

    @Override
    @Transactional(rollbackOn = MailException.class)
    public void save(CreateUserRequest userRequest) {
        try {
            userRequest.setPassword(passwordEncoder.encode(userRequest.getPassword()));
            User user = mapper.forRequest().map(userRequest, User.class);
            if (user == null)
                throw new ResourceNotFoundException();
            user.setActivationToken(UUID.randomUUID().toString());
            user.setActive(false);
            userRepository.saveAndFlush(user);
            emailService.sendActivationEmail(user.getEmail(), user.getActivationToken());
        } catch (DataIntegrityViolationException e) {
            throw new NotUniqueEmailException();
        } catch (MailException e) {
            throw new ActivationNotificationException();
        }
    }

    @Override
    public void activateUser(String token) {
        User inDB = userRepository.findByActivationToken(token).orElseThrow(() -> new InvalidTokenException());
        if (inDB == null) {
            throw new InvalidTokenException();
        }
        inDB.setActive(true);
        inDB.setActivationToken(null);
        userRepository.save(inDB);
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
