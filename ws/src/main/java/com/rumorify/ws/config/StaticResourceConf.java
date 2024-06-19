package com.rumorify.ws.config;

import java.nio.file.Paths;
import java.util.concurrent.TimeUnit;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.CacheControl;
import org.springframework.lang.NonNull;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class StaticResourceConf implements WebMvcConfigurer {
    private final RumorifyProperties props;
    private static final Logger logger = LogManager.getLogger(StaticResourceConf.class);

    public StaticResourceConf(RumorifyProperties props) {
        this.props = props;
    }

    @Override
    public void addResourceHandlers(@NonNull ResourceHandlerRegistry registry) {
        logger.debug("addResourceHandlers running...");
        String rootPath = Paths.get(props.getStorage().getRoot()).toAbsolutePath().toString();
        registry.addResourceHandler("/assets/**").addResourceLocations("file:" + rootPath + "/")
                .setCacheControl(CacheControl.maxAge(365, TimeUnit.DAYS));
    }

    @Bean
    CommandLineRunner createStorageDirectories() {
        logger.debug("createStorageDirectories running...");
        return args -> {
            Paths.get(props.getStorage().getRoot()).toFile().mkdirs();
            Paths.get(props.getStorage().getRoot(), props.getStorage().getProfile()).toFile().mkdirs();
            Paths.get(props.getStorage().getRoot(), props.getStorage().getPost()).toFile().mkdirs();
        };
    }
}
