package com.example.demo.seeders;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.demo.models.User;
import com.example.demo.repository.RoleRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.UserService;

@Component
public class UserSeeder implements TableSeeder {

	private UserRepository userRepository;
	private RoleRepository roleRepository;
	private UserService userService;

	public UserSeeder(UserRepository userRepository, RoleRepository roleRepository, UserService userService) {
		this.userRepository = userRepository;
		this.roleRepository = roleRepository;
		this.userService = userService;
	}

	public void seed() {
		if (userRepository.findAll().size() == 0) {
			User admin = createAdmin("admin@wizardery.ch", "Administrator", "password", "", "", true);
			userService.save(admin);
			User user = createUser("user@wizardery.ch", "User", "password", "", "", false);
			userService.save(user);
		}
	}

	private User createUser(String email, String username, String password, String description, String imageUrl,
			boolean isPublic) {
		User user = new User();
		user.setEmail(email);
		user.setPassword(password);
		user.setImageUrl(imageUrl);
		user.setDescription(description);
		user.setRole(roleRepository.findByName("USER"));
		user.setUsername(username);
		user.setPublic(isPublic);
		return user;
	}

	private User createAdmin(String email, String username, String password, String description, String imageUrl,
			boolean isPublic) {
		User user = new User();
		user.setEmail(email);
		user.setPassword(password);
		user.setImageUrl(imageUrl);
		user.setDescription(description);
		user.setRole(roleRepository.findByName("ADMIN"));
		user.setUsername(username);
		user.setPublic(isPublic);
		return user;
	}

}
