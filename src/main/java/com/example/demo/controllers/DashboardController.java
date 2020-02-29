package com.example.demo.controllers;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DashboardController {

	@GetMapping(value = { "/", "/dashboard" })
	public String showDashboard() {
		if (SecurityContextHolder.getContext().getAuthentication() != null)
		{
			System.out.println("LOGGED IN");
		}
		return "dashboard";
	}
	
	@GetMapping(value = "/creategame")
	public String showRpgCreate() {
		return "rpg-create";
	}
	
	@GetMapping(value = "/test")
	public String showTest() {
		return "test";
	}
}
