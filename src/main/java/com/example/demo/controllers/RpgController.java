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
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.models.File;
import com.example.demo.models.Rpg;
import com.example.demo.models.User;
import com.example.demo.repositories.RpgRepository;
import com.example.demo.repositories.UserRepository;
import com.example.demo.services.StorageService;
import com.example.demo.utils.Routes;
import com.example.demo.validators.RpgValidator;

@Controller
public class RpgController {

	private static final String FILE_DIRECTORY = System.getProperty("user.dir")
			+ "/src/main/resources/static/public/rpg/";

	@Autowired
	private RpgRepository rpgRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private RpgValidator rpgValidator;

	@GetMapping(Routes.RPG_DETAILS + "{id}")
	public String showRpg(Model model, @PathVariable Long id, Principal principal) {
		Optional<Rpg> optionalRpg = rpgRepository.findById(id);
		optionalRpg.ifPresent(rpg -> model.addAttribute("rpg", rpg));

		if (principal != null) {
			User authUser = userRepository.findByEmail(principal.getName());
			model.addAttribute("user", authUser);
			if (optionalRpg.isPresent()) {
				boolean hasFavourite = optionalRpg.get().getUsers().stream()
						.anyMatch(u -> u.getId() == authUser.getId());
				model.addAttribute("hasFavourite", hasFavourite);
			}
		}

		return "rpg-details";
	}

	@PostMapping(Routes.RPG_DETAILS + "{id}" + "/addToFavourite")
	public String addToFavourite(@ModelAttribute User user, Model model, @PathVariable Long id) {
		Optional<User> optionalUser = userRepository.findById(user.getId());
		Optional<Rpg> optionalRpg = rpgRepository.findById(id);
		optionalUser.ifPresent(u -> u.addFavoriteRpg(optionalRpg.get()));

		if (optionalUser.isPresent()) {
			userRepository.save(optionalUser.get());
		}

		boolean hasFavourite = true;
		model.addAttribute("hasFavourite", hasFavourite);

		return "redirect:" + Routes.RPG_DETAILS + id;
	}

	@GetMapping(Routes.RPG_CREATE)
	public String showRpgCreate(Model model) {
		model.addAttribute("rpg", new Rpg());
		return "rpg-create";
	}

	@PostMapping(Routes.RPG_CREATE)
	public String insertRpg(@ModelAttribute Rpg rpg, Model model, Principal principal, BindingResult bindingResult) {
		
		rpgValidator.validate(rpg, bindingResult);

		if (bindingResult.hasErrors()) {
			return "rpg-create";
		}
		
		MultipartFile[] multipartFiles = rpg.getUploadedFiles();
		List<File> files = new ArrayList<>();

		if (multipartFiles != null) {
			for (MultipartFile multipartFile : multipartFiles) {
				File file = new File();
				String originalFileName = multipartFile.getOriginalFilename();

				String filePath = StorageService.saveToDisk(multipartFile, FILE_DIRECTORY);

				file.setName(originalFileName);
				file.setRpg(rpg);
				file.setFileLocation(filePath);
				files.add(file);
			}
		}

		User creator = userRepository.findByEmail(principal.getName());

		rpg.setCreator(creator);
		rpg.setFiles(files);
		rpgRepository.save(rpg);
		return "redirect:" + Routes.DASHBOARD;
	}

	@PostMapping(Routes.RPG_DETAILS + "{id}" + "/update/form")
	public String updateRpgForm(@ModelAttribute Rpg rpg, Model model) {

		Optional<Rpg> optionalRpg = rpgRepository.findById(rpg.getId());
		if (optionalRpg.isPresent()) {
			model.addAttribute("rpg", optionalRpg.get());

			return "rpg-update";
		}

		return "redirect:" + Routes.TEST; // a changer par page d'erreur
	}

	@PostMapping(Routes.RPG_DETAILS + "{id}" + "/update")
	public String updateRpg(@ModelAttribute Rpg rpg, BindingResult bindingResult) {

		rpgValidator.validate(rpg, bindingResult);
		if (bindingResult.hasErrors()) {
			return "rpg-update";
		}

		rpgRepository.save(rpg);

		return "redirect:" + Routes.RPG_DETAILS + rpg.getId();
	}

	@PostMapping(Routes.RPG_DETAILS + "{id}" + "/delete")
	public String deleteRpg(@ModelAttribute Rpg rpg) {
		// TODO: faire assert rpg.id() == {id} de l'url pour coverage?
		rpgRepository.deleteById(rpg.getId());

		return "redirect:" + Routes.DASHBOARD;
	}

	@GetMapping(Routes.SCENARIO_CREATE)
	public String showScenarioCreate() {
		return "scenario-create";
	}

	@GetMapping(Routes.SCENARIO)
	public String showScenario() {
		return "scenario-details";
	}
}
