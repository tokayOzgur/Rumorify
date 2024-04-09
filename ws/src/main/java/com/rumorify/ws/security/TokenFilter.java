package com.rumorify.ws.security;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.rumorify.ws.config.CurrentUser;
import com.rumorify.ws.model.User;
import com.rumorify.ws.service.TokenService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class TokenFilter extends OncePerRequestFilter {

    private final TokenService tokenService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String tokenWithPrefix = getTokenWithPrefix(request);
        if (tokenWithPrefix != null) {
            int userId = tokenService.verifyToken(tokenWithPrefix);
            if (userId != 0) {
                // TODO: aktif userlar için kontrol ekle
                System.out.println("User with id " + userId + " is authenticated");
                CurrentUser currentUser = new CurrentUser(User.builder().id(userId).build());
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                        currentUser, null, currentUser.getAuthorities());
                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            } else {
                System.out.println("User is not authenticated");
            }
        }
        filterChain.doFilter(request, response);
    }

    // TODO null dönüyor
    private String getTokenWithPrefix(HttpServletRequest request) {
        var cookies = request.getCookies();
        if (cookies != null) {
            for (var cookie : cookies) {
                if (cookie.getName().equals("rumor-token") && cookie.getValue() != null
                        && !cookie.getValue().isEmpty()) {
                    return "AnyPrefix " + cookie.getValue();
                }
            }
        }
        return request.getHeader("Authorization");
    }

}
