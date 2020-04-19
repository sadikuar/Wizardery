package com.example.demo.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.models.Rpg;
import com.example.demo.repositories.RpgRepository;
import com.example.demo.services.MarkdownParsingService;
import com.example.demo.utils.Routes;

@Controller
public class DashboardController {

	@Autowired
	private RpgRepository rpgRepository;

	@GetMapping(value = { Routes.DASHBOARD, "/dashboard" })
	public String showDashboard(Model model, @RequestParam(required = false) Integer page) {
		page = page == null ? 0 : page - 1;

		Pageable pageable = PageRequest.of(page, 5);
		Page<Rpg> pageRpg = rpgRepository.findAll(pageable);
		List<Rpg> listRpg = pageRpg.getContent();

		for (Rpg rpg : listRpg) {
			MarkdownParsingService.parse(rpg);
		}

		model.addAttribute("rpgs", listRpg);
		model.addAttribute("pageNumber", pageRpg.getNumber());
		model.addAttribute("pageTotal", pageRpg.getTotalPages());
		return "dashboard";
	}

	@GetMapping(Routes.SEARCH)
	public String search(Model model, @RequestParam(required = false, name = "name") String name,  @RequestParam(required = false, name = "orderby") Integer orderby, @RequestParam(required = false) Integer page) {
		page = page == null ? 0 : page - 1;
		
		Pageable pageable = PageRequest.of(page, 5);
		
		if (orderby != null) {
			switch (orderby) {
			case 1:
				pageable = PageRequest.of(page, 5, Sort.by("name").ascending());
				break;
				
			case 2:
				pageable = PageRequest.of(page, 5, Sort.by("name").descending());
				break;
				
			case 3:
				pageable = PageRequest.of(page, 5, Sort.by("creator.username").ascending());
				break;
				
			case 4:
				pageable = PageRequest.of(page, 5, Sort.by("creator.username").descending());
				break;
			}
		}

		Page<Rpg> pageRpg = rpgRepository.findByNameLike("%" + name + "%", pageable);
		
		System.out.println(pageRpg.getTotalPages());

		List<Rpg> listRpg = pageRpg.getContent();

		listRpg.forEach(MarkdownParsingService::parse);

		model.addAttribute("pageNumber", pageRpg.getNumber());
		model.addAttribute("pageTotal", pageRpg.getTotalPages());

		model.addAttribute("rpgs", listRpg);

		model.addAttribute("name", name);
		model.addAttribute("orderby", orderby);

		return "dashboard";
	}

	@GetMapping(Routes.TEST)
	public String showTest(Model model) {
		model.addAttribute("markdown", MarkdownParsingService.parse("This is **SPARTA**\n\nTest"));
		return "test";
	}
}
