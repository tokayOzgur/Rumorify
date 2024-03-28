package com.rumorify.ws.service.impl;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.rumorify.ws.config.CurrentUser;
import com.rumorify.ws.model.User;
import com.rumorify.ws.service.ModelMapperService;
import com.rumorify.ws.service.UserService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AppUserDetailsServiceImpl implements UserDetailsService {
    private final UserService userService;
    private final ModelMapperService mapper;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = mapper.forResponse().map(userService.findByEmail(email), User.class);
        if (user == null) {
            throw new UsernameNotFoundException(email + " is not found");
        }
        return new CurrentUser(user);
    }
}
