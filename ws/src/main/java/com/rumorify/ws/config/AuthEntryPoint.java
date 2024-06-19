package com.rumorify.ws.config;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.web.servlet.HandlerExceptionResolver;

import com.rumorify.ws.shared.Messages;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class AuthEntryPoint implements AuthenticationEntryPoint {
    private static final Logger logger = LogManager.getLogger(AuthEntryPoint.class);
    
    @Autowired
    @Qualifier("handlerExceptionResolver")
    private HandlerExceptionResolver resolver;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
            AuthenticationException authException) throws IOException, ServletException {
        try {
            logger.debug("commence running...");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write(Messages.getMessageForLocale("rumorify.authentication.error.message",
                    LocaleContextHolder.getLocale()));
            response.getWriter().flush();
            response.getWriter().close();
        } catch (Exception e) {
            logger.warn("Exception occurred while handling authentication error", e);
            resolver.resolveException(request, response, null, authException);
        }
    }

}
