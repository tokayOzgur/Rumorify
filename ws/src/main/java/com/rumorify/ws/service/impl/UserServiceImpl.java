package com.rumorify.ws.service.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.mail.MailException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.rumorify.ws.dto.requests.CreateUserRequest;
import com.rumorify.ws.dto.requests.PasswordResetRequest;
import com.rumorify.ws.dto.requests.UpdatePasswordRequest;
import com.rumorify.ws.dto.requests.UpdateUserRequest;
import com.rumorify.ws.dto.responses.GetAllActiveUsersResponse;
import com.rumorify.ws.dto.responses.GetAllUserResponse;
import com.rumorify.ws.dto.responses.GetUserByEmailResponse;
import com.rumorify.ws.dto.responses.GetUserByIdResponse;
import com.rumorify.ws.dto.responses.GetUserByUserNameResponse;
import com.rumorify.ws.exception.AccessDeniedException;
import com.rumorify.ws.exception.ActivationNotificationException;
import com.rumorify.ws.exception.AuthenticationException;
import com.rumorify.ws.exception.InvalidTokenException;
import com.rumorify.ws.exception.ResourceNotFoundException;
import com.rumorify.ws.exception.UserNotFoundException;
import com.rumorify.ws.file.FileService;
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
    private final FileService fileService;

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
            user.setActivationToken(UUID.randomUUID().toString());
            user.setActive(false);
            userRepository.saveAndFlush(user);
            emailService.sendTokenEmail(user.getEmail(), user.getActivationToken(), 0);
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
        if (inDb == null)
            throw new ResourceNotFoundException();
        if (entity.getFirstName() != null)
            inDb.setFirstName(entity.getFirstName());
        if (entity.getLastName() != null)
            inDb.setLastName(entity.getLastName());
        if (entity.getUsername() != null)
            inDb.setUsername(entity.getUsername());
        if (entity.getProfileDescription() != null)
            inDb.setProfileDescription(entity.getProfileDescription());
        if (entity.getImage() != null && !entity.getImage().equals(inDb.getImage())) {
            fileService.deleteFile(inDb.getImage());
            String fileName = fileService.saveBase4StringAsFile(entity.getImage());
            inDb.setImage(fileName);
        }
        userRepository.save(inDb);
    }

    @Override
    public void deleteByUserId(int id) {
        User inDb = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
        if (inDb == null)
            throw new ResourceNotFoundException();
        inDb.setDeleted(true);
        fileService.deleteFile(inDb.getImage());
        userRepository.save(inDb);

    }

    @Override
    public Page<GetAllActiveUsersResponse> findAllByActive(Pageable pageable, int id) {
        Page<User> userPage = userRepository.findAllByActiveAndIsDeletedAndIdNot(true, false, pageable, id);
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

    @Override
    public void resetPassword(PasswordResetRequest passwordResetRequest) {
        User userInDb = userRepository.findByEmail(passwordResetRequest.getEmail())
                .orElseThrow(() -> new ResourceNotFoundException());
        if (userInDb.isDeleted() || !userInDb.isActive()) throw new AccessDeniedException();
        userInDb.setPasswordResetToken(UUID.randomUUID().toString());
        userRepository.save(userInDb);
        emailService.sendTokenEmail(passwordResetRequest.getEmail(), userInDb.getPasswordResetToken(), 1);
    }

    @Override
    public void updatePassword(String token, UpdatePasswordRequest request) {
        User userInDb = userRepository.findByPasswordResetToken(token).orElseThrow(() -> new InvalidTokenException());
        if (userInDb == null)
            throw new InvalidTokenException();
        userInDb.setPassword(passwordEncoder.encode(request.getPassword()));
        userInDb.setPasswordResetToken(null);
        userRepository.save(userInDb);
    }

}
