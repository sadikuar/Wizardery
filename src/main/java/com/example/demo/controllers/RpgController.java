package com.example.demo.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.models.File;
import com.example.demo.models.Rpg;
import com.example.demo.repository.RpgRepository;
import com.example.demo.service.StorageService;
import com.example.demo.utils.Directory;
import com.example.demo.utils.Routes;

import java.io.IOException;
import java.security.MessageDigest;

@Controller
public class RpgController {
	
	
	@Autowired
	private RpgRepository rpgRepository;
	
	@GetMapping(Routes.RPG_DASHBOARD)
	public String showDashboard() {
		return "rpg-details";
	}
	
	@GetMapping(Routes.RPG_CREATE)
	public String showRpgCreate(Model model) {
		model.addAttribute("rpg", new Rpg());
		return "rpg-create";
	}
	
	@PostMapping(Routes.RPG_CREATE)
	public String insertRpg(@ModelAttribute Rpg rpg,Model model) {
		MultipartFile[] multipartFiles = rpg.getUploadedFiles();
		List<File> files = new ArrayList<File>();
		
		if(multipartFiles!=null) {
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
		
		rpg.setFiles(files);
		rpgRepository.save(rpg);
		return "redirect:"+Routes.DASHBOARD;
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
