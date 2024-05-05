package com.rumorify.ws.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    @Autowired
    TokenFilter tokenFilter;

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests((authentication) -> authentication
            .requestMatchers(AntPathRequestMatcher.antMatcher(HttpMethod.GET, "/api/v1/users")).authenticated()
            .requestMatchers(AntPathRequestMatcher.antMatcher(HttpMethod.GET, "/api/v1/users/all")).authenticated()
            .requestMatchers(AntPathRequestMatcher.antMatcher(HttpMethod.PUT, "/api/v1/users/{id}")).authenticated()
            .requestMatchers(AntPathRequestMatcher.antMatcher(HttpMethod.DELETE, "/api/v1/users/{id}")).authenticated()
            .requestMatchers(AntPathRequestMatcher.antMatcher(HttpMethod.GET, "/api/v1/users/{id}")).authenticated()
            .requestMatchers(AntPathRequestMatcher.antMatcher(HttpMethod.GET, "/api/v1/auth")).authenticated()
            .requestMatchers(AntPathRequestMatcher.antMatcher("/assets/**")).authenticated()
            .requestMatchers(AntPathRequestMatcher.antMatcher("/api/v1/posts/**")).authenticated()
            .anyRequest().permitAll());

        http.httpBasic(httpBasic -> httpBasic.authenticationEntryPoint(new AuthEntryPoint()));
        http.csrf(crsf -> crsf.disable());
        http.headers(headers -> headers
                .contentSecurityPolicy(csp -> csp
                        .policyDirectives("script-src 'self'; object-src 'none';")));

        http.addFilterBefore(tokenFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

}
