package com.example.demo.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.models.Rpg;
import com.example.demo.models.Scenario;
import com.example.demo.repositories.RpgRepository;
import com.example.demo.repositories.ScenarioRepository;
import com.example.demo.services.MarkdownParsingService;
import com.example.demo.utils.Routes;

@Controller
public class DashboardController {

	@Autowired
	private RpgRepository rpgRepository;

	@Autowired
	private ScenarioRepository scenarioRepository;

	@GetMapping(value = { Routes.DASHBOARD, "/dashboard" })
	public String showDashboard(Model model) {
		List<Rpg> listRpg = rpgRepository.findAll(PageRequest.of(0, 5)).getContent();
		for (Rpg rpg : listRpg) {
			MarkdownParsingService.parse(rpg);
		}
		model.addAttribute("rpgs", listRpg);

		return "dashboard";
	}

	@GetMapping(Routes.SEARCH)
	public String search(Model model, @RequestParam(name = "name") String name, @RequestParam(name = "type") int type) {
		List<Rpg> listRpg = new ArrayList<>();
		List<Scenario> listScenarios = new ArrayList<>();

		switch (type) {
		case 0:
			listRpg = rpgRepository.findByNameLike("%" + name + "%");
			listScenarios = scenarioRepository.findByNameLike("%" + name + "%");
			break;

		case 1:
			listRpg = rpgRepository.findByNameLike("%" + name + "%");
			break;

		case 2:
			listScenarios = scenarioRepository.findByNameLike("%" + name + "%");
			break;

		default:
			break;
		}
		
		listRpg.forEach(MarkdownParsingService::parse);
		listScenarios.forEach(MarkdownParsingService::parse);

		model.addAttribute("rpgs", listRpg);
		model.addAttribute("scenarios", listScenarios);
		model.addAttribute("rpg_count", listRpg.size() > 0 ? listRpg.size() : null);
		model.addAttribute("scenario_count", listScenarios.size() > 0 ? listScenarios.size() : null);
		
		model.addAttribute("name", name);
		model.addAttribute("type", type);
		
		return "dashboard";
	}

	@GetMapping(Routes.TEST)
	public String showTest(Model model) {
		model.addAttribute("markdown", MarkdownParsingService.parse("This is **SPARTA**\n\nTest"));
		return "test";
	}
}
