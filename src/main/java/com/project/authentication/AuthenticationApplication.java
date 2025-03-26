package com.project.authentication;

import com.project.authentication.entities.Role;
import com.project.authentication.repositories.RoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class AuthenticationApplication {

	public static void main(String[] args) {
		SpringApplication.run(AuthenticationApplication.class, args);
	}

	@Bean
	CommandLineRunner initDatabase(RoleRepository roleRepository) {
		return args -> {
			if (roleRepository.count() == 0) {
				roleRepository.save(new Role("ROLE_ADMIN"));
				roleRepository.save(new Role("ROLE_USER"));
			}
		};
	}
}