package com.example.demo.seeders;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.example.demo.repository.RoleRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.UserService;

@Component
public class Seeder {

	private UserRepository userRepository;
	private RoleRepository roleRepository;
	private UserService userService;

	public Seeder(UserRepository userRepository, RoleRepository roleRepository, UserService userService) {
		this.userRepository = userRepository;
		this.roleRepository = roleRepository;
		this.userService = userService;
	}

	@EventListener
	public void seed(ContextRefreshedEvent event) {
		seedRolesTable();
		seedUsersTable();
	}

	private void seedRolesTable() {
		RoleSeeder roleSeeder = new RoleSeeder(roleRepository);
		roleSeeder.seed();
	}

	private void seedUsersTable() {
		UserSeeder userSeeder = new UserSeeder(userRepository, roleRepository, userService);
		userSeeder.seed();
	}
}
