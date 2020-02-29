package com.example.demo.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DashboardController {

	@GetMapping(value = { "/", "/dashboard" })
	public String showDashboard() {
		return "dashboard";
	}

	@GetMapping(value = "/signin")
	public String showSignin() {
		return "signin";
	}
	
	@GetMapping(value = "/signup")
	public String showSignup() {
		return "signup";
	}
}
