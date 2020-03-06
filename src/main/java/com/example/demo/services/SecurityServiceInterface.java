package com.example.demo.services;

public interface SecurityServiceInterface {

	String findLoggedInUsername();
	
	void autoLogin(String email, String password);
}
