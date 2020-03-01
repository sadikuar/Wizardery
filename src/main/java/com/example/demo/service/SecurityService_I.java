package com.example.demo.service;

public interface SecurityService_I {

	String findLoggedInUsername();
	
	void autoLogin(String email, String password);
}
