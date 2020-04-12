package com.example.demo.seeders;

import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.example.demo.repositories.FileRepository;
import com.example.demo.repositories.RoleRepository;
import com.example.demo.repositories.RpgRepository;
import com.example.demo.repositories.ScenarioRepository;
import com.example.demo.repositories.UserRepository;
import com.example.demo.services.UserService;

@Component
public class Seeder {

	private UserRepository userRepository;
	private RoleRepository roleRepository;
	private UserService userService;
	private RpgRepository rpgRepository;
	private ScenarioRepository scenarioRepository;
	private FileRepository fileRepository;

	public Seeder(UserRepository userRepository, RoleRepository roleRepository, UserService userService,
			RpgRepository rpgRepository, ScenarioRepository scenarioRepository, FileRepository fileRepository) {
		this.userRepository = userRepository;
		this.roleRepository = roleRepository;
		this.userService = userService;
		this.rpgRepository = rpgRepository;
		this.scenarioRepository = scenarioRepository;
		this.fileRepository = fileRepository;
	}

	@EventListener
	public void seed(ContextRefreshedEvent event) {
		seedRolesTable();
		seedUsersTable();
		seedRpgTable();
		seedScenarioTable();
		seedFileTable();
	}

	private void seedRolesTable() {
		RoleSeeder roleSeeder = new RoleSeeder(roleRepository);
		roleSeeder.seed();
	}

	private void seedUsersTable() {
		UserSeeder userSeeder = new UserSeeder(userRepository, roleRepository, userService);
		userSeeder.seed();
	}

	private void seedRpgTable() {
		RpgSeeder rpgSeeder = new RpgSeeder(rpgRepository, userRepository);
		rpgSeeder.seed();
	}

	private void seedScenarioTable() {
		ScenarioSeeder scenarioSeeder = new ScenarioSeeder(scenarioRepository, rpgRepository, userRepository);
		scenarioSeeder.seed();
	}
	
	private void seedFileTable() {
		FileSeeder fileSeeder = new FileSeeder(fileRepository, rpgRepository, scenarioRepository);
		fileSeeder.seed();
	}
}
