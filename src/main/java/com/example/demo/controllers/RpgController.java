package com.example.demo.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.demo.models.Rpg;

@Controller
public class RpgController {
	
	@GetMapping(value = {"/rpg"})
	public String showDashboard() {
		return "rpg-details";
	}
	
	@GetMapping("/rpg/create")
	public String showRpgCreate(Model model) {
		model.addAttribute("rpg", new Rpg());
		return "rpg-create";
	}
	
	@GetMapping(value = {"/rpg/createscenario"})
	public String showScenarioCreate() {
		return "scenario-create";
	}
	
	@GetMapping(value = {"/rpg/scenario"})
	public String showScenario() {
		return "scenario-details";
	}
}
