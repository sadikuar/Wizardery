package com.example.demo.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RpgController {
	
	@GetMapping(value = {"/rpg"})
	public String showDashboard() {
		return "rpg-details";
	}

}
