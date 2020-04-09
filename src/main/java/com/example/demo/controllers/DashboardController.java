package com.example.demo.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.demo.models.Rpg;
import com.example.demo.repositories.RpgRepository;
import com.example.demo.services.MarkdownParsingService;
import com.example.demo.utils.Routes;

@Controller
public class DashboardController {

	@Autowired
	private RpgRepository rpgRepository;

	@GetMapping(value = { Routes.DASHBOARD, "/dashboard" })
	public String showDashboard(Model model) {
		List<Rpg> listRpg = rpgRepository.findAll(PageRequest.of(0, 5)).getContent();
		for (Rpg rpg : listRpg) {
			rpg.setDescription(MarkdownParsingService.parse(rpg.getDescription()));
		}
		model.addAttribute("rpgs", listRpg);
		
		return "dashboard";
	}


	@GetMapping(Routes.TEST)
	public String showTest(Model model) {
		model.addAttribute("markdown", MarkdownParsingService.parse("This is **SPARTA**\n\nTest"));
		return "test";
	}
}
