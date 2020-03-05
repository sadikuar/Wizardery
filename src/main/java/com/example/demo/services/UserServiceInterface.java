package com.example.demo.services;

import com.example.demo.models.User;

public interface UserServiceInterface {
	void save(User user);
	
	User findByEmail(String email);
}
