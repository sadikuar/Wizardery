package com.example.demo.seeders;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import com.example.demo.models.Role;
import com.example.demo.repository.RoleRepository;
import com.example.demo.utils.Role_E;

@Component
public class RoleSeeder implements TableSeeder {

	private RoleRepository roleRepository;

	public RoleSeeder(RoleRepository roleRepository) {
		this.roleRepository = roleRepository;
	}

	public void seed() {
		System.out.println(roleRepository);
		if (roleRepository.findAll().size() == 0) {
			Role role = new Role();
			role.setName(""+Role_E.USER);
			roleRepository.save(role);
			role = new Role();
			role.setName(""+Role_E.ADMIN);
			roleRepository.save(role);
		}

	}
}
