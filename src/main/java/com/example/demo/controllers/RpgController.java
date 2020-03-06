package com.example.demo.controllers;

import java.io.IOException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.models.File;
import com.example.demo.models.Rpg;
import com.example.demo.models.User;
import com.example.demo.repositories.FileRepository;
import com.example.demo.repositories.RpgRepository;
import com.example.demo.repositories.UserRepository;
import com.example.demo.services.StorageService;
import com.example.demo.utils.Directory;
import com.example.demo.utils.Routes;

@Controller
public class RpgController {

	@Autowired
	private RpgRepository rpgRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private FileRepository fileRepository;

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

	@PostMapping(Routes.RPG_DETAILS + "{id}" + "/removeFromFavourite")
	public String removeFromFavourite(@ModelAttribute User user, Model model, @PathVariable Long id) {
		Optional<User> optionalUser = userRepository.findById(user.getId());
		Optional<Rpg> optionalRpg = rpgRepository.findById(id);
		optionalUser.ifPresent(u -> u.removeFavoriteRPG(optionalRpg.get()));

		if (optionalUser.isPresent()) {
			userRepository.save(optionalUser.get());
		}

		boolean hasFavourite = false;
		model.addAttribute("hasFavourite", hasFavourite);

		return "redirect:" + Routes.RPG_DETAILS + id;
	}

	@GetMapping(Routes.RPG_CREATE)
	public String showRpgCreate(Model model) {
		model.addAttribute("rpg", new Rpg());
		return "rpg-create";
	}

	@PostMapping(Routes.RPG_CREATE)
	public String insertRpg(@ModelAttribute Rpg rpg, Model model, Principal principal) {
		MultipartFile[] multipartFiles = rpg.getUploadedFiles();
		List<File> files = new ArrayList<>();

		if (multipartFiles != null) {
			for (MultipartFile multipartFile : multipartFiles) {
				File file = new File();
				String originalFileName = multipartFile.getOriginalFilename();

				String filePath = StorageService.saveToDisk(multipartFile, Directory.RPG_DIR);

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

	@GetMapping(Routes.RPG_DETAILS + "{id}" + "/download/{fileId}")
	public ResponseEntity<Resource> download(@PathVariable Long id, @PathVariable Long fileId) throws IOException {
		Optional<File> f = fileRepository.findById(fileId);
		if (f.isPresent()) {
			File file = f.get();
			java.io.File diskFile = new java.io.File(file.getFileLocation());
			ResponseEntity<Resource> response = StorageService.downloadFromDisk(diskFile, file.getName());
			if (response == null) {
				throw new DataAccessResourceFailureException("not found");
			}
		}
		throw new DataAccessResourceFailureException("not found");
	}

	@ResponseStatus(HttpStatus.NOT_FOUND) // Or @ResponseStatus(HttpStatus.NO_CONTENT)
	@ExceptionHandler(DataAccessResourceFailureException.class)
	public String handleNotFound(DataAccessResourceFailureException ex, RedirectAttributes redirectAttrs) {
		return "forward:" + Routes.DASHBOARD;
	}

	@GetMapping(Routes.SCENARIO_CREATE)
	public String showScenarioCreate() {
		return "scenario-create";
	}

	@GetMapping(Routes.SCENARIO_DETAILS)
	public String showScenario() {
		return "scenario-details";
	}
}
