package com.example.demo.controllers;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.models.File;
import com.example.demo.models.Rpg;
import com.example.demo.models.Scenario;
import com.example.demo.models.User;
import com.example.demo.repositories.RpgRepository;
import com.example.demo.repositories.ScenarioRepository;
import com.example.demo.repositories.UserRepository;
import com.example.demo.services.MarkdownParsingService;
import com.example.demo.services.StorageService;
import com.example.demo.utils.Directory;
import com.example.demo.utils.Routes;
import com.example.demo.validators.ScenarioValidator;

@Controller
public class ScenarioController {

	@Autowired
	private ScenarioRepository scenarioRepository;

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private RpgRepository rpgRepository;

	@Autowired
	private ScenarioValidator scenarioValidator;

	@GetMapping(Routes.RPG_DETAILS + "{id}" + Routes.SCENARIO_CREATE)
	public String showScenarioCreate(Model model, @PathVariable Long id) {
		model.addAttribute("scenario", new Scenario());
		model.addAttribute("rpgId", id);
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

	@PostMapping(Routes.SCENARIO_CREATE)
	public String insertScenario(@ModelAttribute Scenario scenario, @RequestParam("rpgId") Long id,Model model, Principal principal,
			BindingResult bindingResult) {
		
		scenarioValidator.validate(scenario, bindingResult);
		
		if (bindingResult.hasErrors()) {
			return "scenario-create";
		}
		
		MultipartFile[] multipartFiles = scenario.getUploadedFiles();
		List<File> files = new ArrayList<>();
		
		if (multipartFiles != null) {
			for (MultipartFile multipartFile : multipartFiles) {
				File file = new File();
				String originalFileName = multipartFile.getOriginalFilename();

				String filePath = StorageService.saveToDisk(multipartFile, Directory.RPG_DIR);

				file.setName(originalFileName);
				file.setScenario(scenario);
				file.setFileLocation(filePath);
				files.add(file);
			}
		}
		
		User creator = userRepository.findByEmail(principal.getName());
		Optional<Rpg> optionalRpg = rpgRepository.findById(id);
		
		if (optionalRpg.isPresent()) {
			scenario.setRpg(optionalRpg.get());
		}
		
		scenario.setCreator(creator);
		
		scenarioRepository.save(scenario);
		
		return "redirect:" + Routes.RPG_DETAILS + id;
	}
}
