package com.example.demo.controllers;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DashboardController {

	@GetMapping(value = { "/", "/dashboard" })
	public String showDashboard() {
		// tester si user connecté 
//		if (SecurityContextHolder.getContext().getAuthentication() != null && SecurityContextHolder.getContext().getAuthentication().isAuthenticated() && !(SecurityContextHolder.getContext().getAuthentication() 
//		          instanceof AnonymousAuthenticationToken))
//		{
//			System.out.println(SecurityContextHolder.getContext().getAuthentication().getName());
//			System.out.println("LOGGED IN");
//		}
//		else
//		{
//			
//			System.out.println("NOT LOGGED IN");
//		}
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
