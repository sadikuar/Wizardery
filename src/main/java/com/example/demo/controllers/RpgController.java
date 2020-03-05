package com.example.demo.controllers;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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

@Controller
public class RpgController {

	private static final String FILE_DIRECTORY = System.getProperty("user.dir")
			+ "/src/main/resources/static/public/rpg/";

	@Autowired
	private RpgRepository rpgRepository;

	@Autowired
	private UserRepository userRepository;

	@GetMapping(Routes.RPG_DETAILS + "{id}")
	public String showRpg(Model model, @PathVariable Long id) {

		Optional<Rpg> optionalRpg = rpgRepository.findById(id);

		optionalRpg.ifPresent(rpg -> model.addAttribute("rpg", rpg));

		return "rpg-details";
	}

	@GetMapping(Routes.RPG_CREATE)
	public String showRpgCreate(Model model) {
		model.addAttribute("rpg", new Rpg());
		return "rpg-create";
	}

	@PostMapping(Routes.RPG_CREATE)
	public String insertRpg(@ModelAttribute Rpg rpg, Model model, Principal principal) {
		MultipartFile[] multipartFiles = rpg.getUploadedFiles();
		List<File> files = new ArrayList<File>();

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

	@GetMapping(Routes.SCENARIO_CREATE)
	public String showScenarioCreate() {
		return "scenario-create";
	}

	@GetMapping(Routes.SCENARIO)
	public String showScenario() {
		return "scenario-details";
	}
}
