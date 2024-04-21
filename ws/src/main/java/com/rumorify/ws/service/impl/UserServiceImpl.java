package com.rumorify.ws.service.impl;

import java.util.List;
import java.util.UUID;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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
    private static final Logger logger = LogManager.getLogger(UserServiceImpl.class); 
    


    @Override
    public GetUserByUserNameResponse findByUsername(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> {
                    logger.error("User not found with username: {}", username);
                    return new UserNotFoundException(username);
                });
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
            logger.debug("User saved successfully: {}", userRequest.getEmail());
        } catch (MailException e) {
            logger.error("Error sending email: {}, user email: {}", e.getMessage(), userRequest.getEmail(), e);
            throw new ActivationNotificationException();
        }
    }

    @Override
    public void activateUser(String token) {
        User inDB = userRepository.findByActivationToken(token).orElseThrow(() -> {
            logger.error("Invalid user activation token: {}", token);
            return new InvalidTokenException();
        });
        inDB.setActive(true);
        inDB.setActivationToken(null);
        userRepository.save(inDB);
    }

    @Override
    public void updateByUserId(int id, UpdateUserRequest entity) {
        User inDb = userRepository.findById(id).orElseThrow(() -> {
            logger.error("User not found with id: {}", id);
            return new UserNotFoundException(id);
        });
        if (entity.getFirstName() != null) inDb.setFirstName(entity.getFirstName());
        if (entity.getLastName() != null) inDb.setLastName(entity.getLastName());
        if (entity.getUsername() != null) inDb.setUsername(entity.getUsername());
        if (entity.getProfileDescription() != null) inDb.setProfileDescription(entity.getProfileDescription());
        if (entity.getImage() != null && !entity.getImage().equals(inDb.getImage())) {
            fileService.deleteFile(inDb.getImage());
            String fileName = fileService.saveBase4StringAsFile(entity.getImage());
            inDb.setImage(fileName);
        }
        userRepository.save(inDb);
        logger.debug("User updated successfully: {}", id);
    }

    @Override
    public void deleteByUserId(int id) {
        User inDb = userRepository.findById(id).orElseThrow(() -> {
            logger.error("User not found with id: {}", id);
            return new UserNotFoundException(id);
        });
        inDb.setDeleted(true);
        fileService.deleteFile(inDb.getImage());
        userRepository.save(inDb);
        logger.debug("User deleted successfully: {}", id);
    }

    @Override
    public Page<GetAllActiveUsersResponse> findAllByActive(Pageable pageable, int id) {
        Page<User> userPage = userRepository.findAllByActiveAndIsDeletedAndIdNot(true, false, pageable, id);
        return userPage.map(user -> this.mapper.forResponse().map(user, GetAllActiveUsersResponse.class));
    }

    @Override
    public List<GetAllUserResponse> findAll() {
        return userRepository.findAll().stream()
                .map(user -> this.mapper.forResponse().map(user, GetAllUserResponse.class)).toList();
    }

    @Override
    public GetUserByIdResponse findById(int id) {
        User user = userRepository.findById(id).orElseThrow(() -> {
            logger.error("User not found with id: {}", id);
            return new UserNotFoundException(id);
        });
        return mapper.forResponse().map(user, GetUserByIdResponse.class);
    }

    @Override
    public GetUserByEmailResponse findByEmail(String email) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> {
            logger.error("User not found with email: {}", email);
            return new AuthenticationException();
        });
        return mapper.forResponse().map(user, GetUserByEmailResponse.class);
    }

    @Override
    public void resetPassword(PasswordResetRequest passwordResetRequest) {
        User userInDb = userRepository.findByEmail(passwordResetRequest.getEmail()).orElseThrow(() -> {
            logger.error("User not found with email: {}", passwordResetRequest.getEmail());
            return new ResourceNotFoundException();
        });
        if (userInDb.isDeleted() || !userInDb.isActive()) {
            logger.debug("User is not active or deleted: {}", passwordResetRequest.getEmail());
            throw new AccessDeniedException();
        }
        userInDb.setPasswordResetToken(UUID.randomUUID().toString());
        userRepository.save(userInDb);
        emailService.sendTokenEmail(passwordResetRequest.getEmail(), userInDb.getPasswordResetToken(), 1);
    }

    @Override
    public void updatePassword(String token, UpdatePasswordRequest request) {
        User userInDb = userRepository.findByPasswordResetToken(token).orElseThrow(() -> {
            logger.error("Invalid password reset token: {}", token);
            return new InvalidTokenException();
        });
        userInDb.setPassword(passwordEncoder.encode(request.getPassword()));
        userInDb.setPasswordResetToken(null);
        userRepository.save(userInDb);
        logger.debug("Password updated successfully: {}", userInDb.getEmail());
    }

}
