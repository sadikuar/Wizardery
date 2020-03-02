package com.example.demo.service;

import com.example.demo.models.User;

public interface UserService_I {
	void save(User user);
	
	User findByEmail(String email);
}
