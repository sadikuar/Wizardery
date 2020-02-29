package com.example.demo.service;

public interface SecurityService {

	String findLoggedInUsername();
	
	void autoLogin(String email, String password);
}
