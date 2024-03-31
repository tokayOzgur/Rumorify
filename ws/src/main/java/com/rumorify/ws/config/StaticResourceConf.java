package com.rumorify.ws.config;

import java.nio.file.Paths;
import java.util.concurrent.TimeUnit;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.CacheControl;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import lombok.AllArgsConstructor;

@Configuration
@EnableWebMvc
@AllArgsConstructor
public class StaticResourceConf implements WebMvcConfigurer {
    private final RumorifyProperties props;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        String path = Paths.get(props.getStorage().getRoot()).toAbsolutePath().toString();

        registry.addResourceHandler("/assets/**").addResourceLocations("file:" + path + "/")
                .setCacheControl(CacheControl.maxAge(
                        365, TimeUnit.DAYS));
    }

    @Bean
    CommandLineRunner createStorageDirectories() {
        return args -> {
            Paths.get(props.getStorage().getRoot()).toFile().mkdirs();
            Paths.get(props.getStorage().getRoot(), props.getStorage().getProfile()).toFile().mkdirs();
            Paths.get(props.getStorage().getRoot(), props.getStorage().getPost()).toFile().mkdirs();
        };
    }
}
