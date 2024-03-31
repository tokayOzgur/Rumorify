package com.rumorify.ws.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.lang.NonNull;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import lombok.AllArgsConstructor;

@Configuration
@AllArgsConstructor
public class WebConfig implements WebMvcConfigurer {
        private final RumorifyProperties props;

        @Override
        public void addCorsMappings(@NonNull CorsRegistry registry) {
                registry.addMapping("/api/**")
                                .allowedOrigins("http://localhost:5173")
                                .allowedMethods("GET", "POST", "PUT", "DELETE", "PATCH")
                                .allowedHeaders("*")
                                .allowCredentials(true);
        }

        @Override
        public void addResourceHandlers(ResourceHandlerRegistry registry) {
                registry.addResourceHandler("/assets/profiles/**")
                                .addResourceLocations(
                                                "file:" + props.getStorage().getRoot() + "/"
                                                                + props.getStorage().getProfile() + "/");
                registry.addResourceHandler("/assets/posts/**")
                                .addResourceLocations(
                                                "file:" + props.getStorage().getRoot() + "/"
                                                                + props.getStorage().getPost()
                                                                + "/");
        }
}
