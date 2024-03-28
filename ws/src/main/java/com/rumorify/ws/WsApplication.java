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
			for (int i = 1; i < 25; i++) {
				User user = new User();
				user.setUsername("User " + i);
				user.setFirstName("firstName " + i);
				user.setLastName("lastName " + i);
				user.setProfileDescription("Hi! I'm user " + i);
				user.setEmail("user" + i + "@gmail.com");
				user.setPassword(passwordEncoder.encode("P4ssword!"));
				user.setActive(true);
				user.setImage("https://i.pravatar.cc/600?img=" + i);
				userRepository.save(user);
			}
			User user = new User();
			user.setUsername("User " + 26);
			user.setFirstName("firstName " + 26);
			user.setLastName("lastName " + 26);
			user.setProfileDescription("Hi! I'm user " + 26);
			user.setEmail("user" + 26 + "@gmail.com");
			user.setPassword(passwordEncoder.encode("P4ssword!"));
			user.setActive(false);
			user.setImage("https://i.pravatar.cc/600?img=" + 26);
			userRepository.save(user);
		};
	}
}