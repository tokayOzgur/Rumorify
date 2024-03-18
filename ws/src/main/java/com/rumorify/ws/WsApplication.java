package com.rumorify.ws;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.rumorify.ws.model.User;
import com.rumorify.ws.repository.UserRepository;

@SpringBootApplication
public class WsApplication {

	public static void main(String[] args) {
		SpringApplication.run(WsApplication.class, args);
	}

	@Bean
	@Profile("dev")
	CommandLineRunner userCreator(UserRepository userRepository, PasswordEncoder passwordEncoder) {
		return args -> {
			for (int i = 0; i < 25; i++) {
				User user = new User();
				user.setUsername("User " + i);
				user.setFirstName("firstName " + i);
				user.setLastName("lastName " + i);
				user.setProfileDescription("Hi! I'm user " + i);
				user.setEmail("user" + i + "@gmail.com");
				user.setPassword(passwordEncoder.encode("P4assword!"));
				user.setActive(true);
				user.setImage("https://i.pravatar.cc/600?img=" + i);
				userRepository.save(user);
			}
		};
	}
}