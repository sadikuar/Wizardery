package com.example.demo.controllers;

import java.security.Principal;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.demo.models.Scenario;
import com.example.demo.models.User;
import com.example.demo.repositories.ScenarioRepository;
import com.example.demo.repositories.UserRepository;
import com.example.demo.services.MarkdownParsingService;
import com.example.demo.utils.Routes;

@Controller
public class ScenarioController {
	
	@Autowired
	ScenarioRepository scenarioRepository;
	
	@Autowired
	UserRepository userRepository;
	
	@GetMapping(Routes.SCENARIO_CREATE)
	public String showScenarioCreate() {
		return "scenario-create";
	}

	@GetMapping(Routes.SCENARIO_DETAILS + "{id}")
	public String showScenario(Model model, @PathVariable Long id, Principal principal) {
		Optional<Scenario> optionalScenario = scenarioRepository.findById(id);
		optionalScenario.ifPresent(scenario -> {
			MarkdownParsingService.parse(scenario);
			model.addAttribute("scenario", scenario);
			});

		if (principal != null) {
			User authUser = userRepository.findByEmail(principal.getName());
			model.addAttribute("user", authUser);
			if (optionalScenario.isPresent()) {
				boolean hasFavourite = optionalScenario.get().getUsers().stream()
						.anyMatch(u -> u.getId() == authUser.getId());
				model.addAttribute("hasFavourite", hasFavourite);
			}
		}
		return "scenario-details";
	}
}
