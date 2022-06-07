package com.tech.blog;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.util.matcher.NegatedServerWebExchangeMatcher;

import com.tech.blog.config.AppConstants;
import com.tech.blog.entity.Role;
import com.tech.blog.repository.RoleRepository;

@SpringBootApplication
public class BlogApplication implements CommandLineRunner {

	@Autowired
	private PasswordEncoder encoder;

	@Autowired
	private RoleRepository roleRepository;

	public static void main(String[] args) {
		SpringApplication.run(BlogApplication.class, args);
	}

	@Bean
	public ModelMapper modelMapper() {

		return new ModelMapper();
	}

	@Override
	public void run(String... args) throws Exception {

		try {

			Role role = new Role();

			role.setId(AppConstants.ROLE_ADMIN);
			role.setName("ROLE_ADMIN");

			Role role1 = new Role();
			
			role1.setId(AppConstants.ROLE_USER);
			role1.setName("ROLE_USER");
			
			List<Role> roles = new ArrayList<>();
			roles.add(role);
			roles.add(role1);
			
			this.roleRepository.saveAll(roles);

		} catch (Exception e) {
			throw new Exception("Error while creating role");
		}

	}

}
