package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.models.Role;
import com.example.demo.models.User;

public interface RoleRepository extends JpaRepository<Role, Long>{

	Role findByName(String name);
	
}
