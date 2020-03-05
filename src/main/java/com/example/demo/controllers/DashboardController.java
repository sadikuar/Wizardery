package com.example.demo.controllers;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.demo.utils.Routes;

@Controller
public class DashboardController {

	@GetMapping(value = { Routes.DASHBOARD, "/dashboard" })
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
	
	@GetMapping(Routes.TEST)
	public String showTest() {
		return "test";
	}
}
