package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.demo.models.User;

public interface UserRepository extends JpaRepository<User, Long>{

	@Query("SELECT u FROM User u WHERE u.email = ?1")
	User findByEmail(String email);
	
}
