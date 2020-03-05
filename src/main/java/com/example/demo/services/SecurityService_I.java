package com.example.demo.services;

public interface SecurityService_I {

	String findLoggedInUsername();
	
	void autoLogin(String email, String password);
}
