package com.rumorify.ws.service.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.mail.MailException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.rumorify.ws.dto.requests.CreateUserRequest;
import com.rumorify.ws.dto.requests.UpdateUserRequest;
import com.rumorify.ws.dto.responses.GetAllActiveUsersResponse;
import com.rumorify.ws.dto.responses.GetAllUserResponse;
import com.rumorify.ws.dto.responses.GetUserByEmailResponse;
import com.rumorify.ws.dto.responses.GetUserByIdResponse;
import com.rumorify.ws.dto.responses.GetUserByUserNameResponse;
import com.rumorify.ws.exception.ActivationNotificationException;
import com.rumorify.ws.exception.AuthenticationException;
import com.rumorify.ws.exception.InvalidTokenException;
import com.rumorify.ws.exception.NotUniqueEmailException;
import com.rumorify.ws.exception.ResourceNotFoundException;
import com.rumorify.ws.exception.UserNotFoundException;
import com.rumorify.ws.model.User;
import com.rumorify.ws.repository.UserRepository;
import com.rumorify.ws.service.EmailService;
import com.rumorify.ws.service.ModelMapperService;
import com.rumorify.ws.service.UserService;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

/**
 * @author tokay
 */
@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;
    private ModelMapperService mapper;
    private EmailService emailService;
    private PasswordEncoder passwordEncoder;

    @Override
    public GetUserByUserNameResponse findByUsername(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException(username));
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
    public void updateByUserId(int id, UpdateUserRequest entity) {
        User inDb = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
        if (inDb == null) throw new ResourceNotFoundException();
        if (entity.getFirstName() != null) inDb.setFirstName(entity.getFirstName());
        if (entity.getLastName() != null) inDb.setLastName(entity.getLastName());
        if (entity.getUsername() != null) inDb.setUsername(entity.getUsername());
        if (entity.getProfileDescription() != null) inDb.setProfileDescription(entity.getProfileDescription());
        if (entity.getImage() != null) inDb.setImage(entity.getImage());
        userRepository.save(inDb);
    }

    @Override
    public void deleteByUserId(int id) {
        throw new UnsupportedOperationException("Unimplemented method 'deleteByUsername'");

    }

    @Override
    public Page<GetAllActiveUsersResponse> findAllByActive(Pageable pageable, int id) {
        Page<User> userPage = userRepository.findAllByActiveAndIdNot(true, pageable, id);
        return userPage.map(user -> this.mapper.forResponse().map(user, GetAllActiveUsersResponse.class));
    }

    @Override
    public List<GetAllUserResponse> findAll() {
        return userRepository.findAll().stream()
                .map(user -> this.mapper.forResponse().map(user, GetAllUserResponse.class))
                .toList();
    }

    @Override
    public GetUserByIdResponse findById(int id) {
        User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
        return mapper.forResponse().map(user, GetUserByIdResponse.class);
    }

    @Override
    public GetUserByEmailResponse findByEmail(String email) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new AuthenticationException());
        return mapper.forResponse().map(user, GetUserByEmailResponse.class);
    }

}
