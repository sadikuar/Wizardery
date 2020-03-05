package com.example.demo.seeders;

import org.springframework.stereotype.Component;

import com.example.demo.models.Role;
import com.example.demo.repositories.RoleRepository;
import com.example.demo.utils.RoleEnum;

@Component
public class RoleSeeder implements TableSeeder {

	private RoleRepository roleRepository;

	public RoleSeeder(RoleRepository roleRepository) {
		this.roleRepository = roleRepository;
	}

	public void seed() {
		if (roleRepository.findAll().isEmpty()) {
			Role role = new Role();
			role.setName(""+RoleEnum.USER);
			roleRepository.save(role);
			role = new Role();
			role.setName(""+RoleEnum.ADMIN);
			roleRepository.save(role);
		}

	}
}
