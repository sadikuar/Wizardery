package com.example.demo.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.demo.utils.Routes;

@Controller
public class ScenarioController {
	
	@GetMapping(Routes.SCENARIO_CREATE)
	public String showScenarioCreate() {
		return "scenario-create";
	}

	@GetMapping(Routes.SCENARIO_DETAILS)
	public String showScenario() {
		return "scenario-details";
	}
}
