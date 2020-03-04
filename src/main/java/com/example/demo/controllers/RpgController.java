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

import java.io.IOException;
import java.security.MessageDigest;

@Controller
public class RpgController {
	
	private static final String FILE_DIRECTORY=System.getProperty("user.dir")+"/src/main/resources/static/public/rpg/";
	private static MessageDigest messageDigest = null;
	
	@Autowired
	private RpgRepository rpgRepository;
	
	@GetMapping(value = {"/rpg"})
	public String showDashboard() {
		return "rpg-details";
	}
	
	@GetMapping("/rpg/create")
	public String showRpgCreate(Model model) {
		model.addAttribute("rpg", new Rpg());
		return "rpg-create";
	}
	
	@PostMapping("/rpg/insert")
	public String insertRpg(@ModelAttribute Rpg rpg,Model model) {
		if(messageDigest==null) {
			try {
				messageDigest=MessageDigest.getInstance("SHA-256");
			} catch (Exception e) {
			}
		}
		MultipartFile[] multipartFiles = rpg.getUploadedFiles();
		List<File> files = new ArrayList<File>();
		if(multipartFiles!=null) {
			for (MultipartFile multipartFile : multipartFiles) {
				File file = new File();
				String originalFileName = multipartFile.getOriginalFilename();
				file.setName(originalFileName);
				
				String[] decomposedFileName = originalFileName.split("\\.");
				String fileExt="."+decomposedFileName[decomposedFileName.length-1];
				String fileName = multipartFile.getOriginalFilename().substring(0, originalFileName.length()-fileExt.length());
				file.setRpg(rpg);
				try {
					messageDigest.update(multipartFile.getBytes());
					String filePath = FILE_DIRECTORY+messageDigest.digest()+fileExt;
					multipartFile.transferTo(new java.io.File(filePath));
					file.setFileLocation(filePath);
				} catch (IllegalStateException | IOException e) {
					e.printStackTrace();
				}
				files.add(file);
			}
		}
		rpg.setFiles(files);
		rpgRepository.save(rpg);
		return "rpg-details";
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
