package com.example.demo.controllers;

import java.io.IOException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.models.File;
import com.example.demo.models.Rpg;
import com.example.demo.models.Scenario;
import com.example.demo.models.User;
import com.example.demo.repositories.FileRepository;
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
	private FileRepository fileRepository;

	@Autowired
	private ScenarioValidator scenarioValidator;

	@GetMapping(Routes.SCENARIO_CREATE)
	public String showScenarioCreate(Model model, @RequestParam(defaultValue = "-1") Long rpgId) {
		if (rpgId != -1) {
			model.addAttribute("scenario", new Scenario());
			model.addAttribute("rpgId", rpgId);

			return "scenario-create";
		}

		return "redirect:error";
	}

	@GetMapping(Routes.SCENARIO_DETAILS + "{id}")
	public String showScenario(Model model, @PathVariable Long id, Principal principal) {
		Optional<Scenario> optionalScenario = scenarioRepository.findById(id);
		optionalScenario.ifPresent(scenario -> {
			MarkdownParsingService.parse(scenario);
			model.addAttribute("scenario", scenario);
			int time = scenario.getTimeApproximation();
			int h = time / 60;
			int m = time % 60;
			StringBuilder sb = new StringBuilder();
			if (h > 0) {
				sb.append(h);
				sb.append("h");
				if (m > 0) {
					sb.append(String.format("%02d", m));
				}
			} else {
				sb.append(m);
				sb.append("m");
			}
			model.addAttribute("parsedTime", sb.toString());
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
	public String insertScenario(@ModelAttribute Scenario scenario, @RequestParam("rpgId") Long id, Model model,
			Principal principal, BindingResult bindingResult) {

		scenarioValidator.validate(scenario, bindingResult);

		if (bindingResult.hasErrors()) {
			return "scenario-create";
		}

		MultipartFile[] multipartFiles = scenario.getUploadedFiles();
		Set<File> files = new HashSet<>();

		if (multipartFiles != null) {
			for (MultipartFile multipartFile : multipartFiles) {
				if (multipartFile.getSize() != 0) {
					File file = new File();
					String originalFileName = multipartFile.getOriginalFilename();

					String filePath = StorageService.saveToDisk(multipartFile, Directory.SCENARIO_DIR);

					file.setName(originalFileName);
					file.setScenario(scenario);
					file.setFileLocation(filePath);
					files.add(file);
				}
			}
		}

		User creator = userRepository.findByEmail(principal.getName());
		Optional<Rpg> optionalRpg = rpgRepository.findById(id);

		if (optionalRpg.isPresent()) {
			scenario.setRpg(optionalRpg.get());
		}
		scenario.setFiles(files);
		scenario.setPatchNote("");

		scenario.setCreator(creator);

		scenarioRepository.save(scenario);

		return "redirect:" + Routes.RPG_DETAILS + id;
	}

	@PostMapping(Routes.SCENARIO_DETAILS + "{id}" + "/update/form")
	public String updateScenarioForm(@ModelAttribute Scenario scenario, Model model) {
		Optional<Scenario> optionalScenario = scenarioRepository.findById(scenario.getId());
		if (optionalScenario.isPresent()) {
			model.addAttribute("scenario", optionalScenario.get());

			return "scenario-update";
		}

		return "redirect:error";
	}

	@PostMapping(Routes.SCENARIO_DETAILS + "{id}" + "/update")
	public String updateRpg(@ModelAttribute Scenario scenario, BindingResult bindingResult) {

		scenarioValidator.validate(scenario, bindingResult);

		if (bindingResult.hasErrors()) {
			return "scenario-update";
		}
		
		MultipartFile[] multipartFiles = scenario.getUploadedFiles();
		Set<File> files = new HashSet<>();

		if (multipartFiles != null) {
			for (MultipartFile multipartFile : multipartFiles) {
				if (multipartFile.getSize() != 0) {
					File file = new File();
					String originalFileName = multipartFile.getOriginalFilename();

					String filePath = StorageService.saveToDisk(multipartFile, Directory.RPG_DIR);

					file.setName(originalFileName);
					file.setScenario(scenario);
					file.setFileLocation(filePath);
					files.add(file);
				}
			}
		
			if(files.size() != 0)
			{
				scenario.setFiles(files);
				
			}else {
				Optional<Scenario> oldScenario = scenarioRepository.findById(scenario.getId());
				oldScenario.ifPresent( old -> {
					scenario.setFiles(old.getFiles());
				});
			}
		}
		scenarioRepository.save(scenario);

		return "redirect:" + Routes.SCENARIO_DETAILS + scenario.getId();
	}

	@PostMapping(Routes.SCENARIO_DETAILS + "{id}" + "/delete")
	public String deleteScenario(@ModelAttribute Scenario scenario) {
		Rpg rpg = scenario.getRpg();
		scenarioRepository.deleteById(scenario.getId());

		return "redirect:" + Routes.RPG_DETAILS + rpg.getId();
	}

	@PostMapping(Routes.SCENARIO_DETAILS + "{id}" + "/addToFavourite")
	public String addToFavourite(@ModelAttribute User user, Model model, @PathVariable Long id) {
		Optional<User> optionalUser = userRepository.findById(user.getId());
		Optional<Scenario> optionalScenario = scenarioRepository.findById(id);
		optionalUser.ifPresent(u -> u.addFavoriteScenario(optionalScenario.get()));

		if (optionalUser.isPresent()) {
			userRepository.save(optionalUser.get());
		}

		boolean hasFavourite = true;
		model.addAttribute("hasFavourite", hasFavourite);

		return "redirect:" + Routes.SCENARIO_DETAILS + id;
	}

	@PostMapping(Routes.SCENARIO_DETAILS + "{id}" + "/removeFromFavourite")
	public String removeFromFavourite(@ModelAttribute User user, Model model, @PathVariable Long id) {
		Optional<User> optionalUser = userRepository.findById(user.getId());
		Optional<Scenario> optionalScenario = scenarioRepository.findById(id);
		optionalUser.ifPresent(u -> u.removeFavoriteScenario(optionalScenario.get()));

		if (optionalUser.isPresent()) {
			userRepository.save(optionalUser.get());
		}

		boolean hasFavourite = false;
		model.addAttribute("hasFavourite", hasFavourite);

		return "redirect:" + Routes.SCENARIO_DETAILS + id;
	}

	@GetMapping(Routes.SCENARIO_DETAILS + "{id}" + "/download/{fileId}")
	public ResponseEntity<Resource> download(@PathVariable Long id, @PathVariable Long fileId) throws IOException {
		Optional<File> f = fileRepository.findById(fileId);
		if (f.isPresent()) {
			File file = f.get();
			java.io.File diskFile = new java.io.File(file.getFileLocation());
			ResponseEntity<Resource> response = StorageService.downloadFromDisk(diskFile, file.getName());
			if (response == null) {
				throw new DataAccessResourceFailureException("not found");
			}
			return response;
		}
		throw new DataAccessResourceFailureException("not found");
	}

	@ResponseStatus(HttpStatus.NOT_FOUND) // Or @ResponseStatus(HttpStatus.NO_CONTENT)
	@ExceptionHandler(DataAccessResourceFailureException.class)
	public String handleNotFound(DataAccessResourceFailureException ex, RedirectAttributes redirectAttrs) {
		return "forward:" + Routes.DASHBOARD;
	}

}
