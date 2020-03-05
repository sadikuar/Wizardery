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
		// tester si user connecté
//		if (SecurityContextHolder.getContext().getAuthentication() != null && SecurityContextHolder.getContext().getAuthentication().isAuthenticated() && !(SecurityContextHolder.getContext().getAuthentication() 
//		          instanceof AnonymousAuthenticationToken))
//		{
//			System.out.println(SecurityContextHolder.getContext().getAuthentication().getName());
//			System.out.println("LOGGED IN");
//		}
//		else
//		{
//			
//			System.out.println("NOT LOGGED IN");
//		}

		List<Rpg> listRpg = rpgRepository.findAll(PageRequest.of(0, 5)).getContent();
		model.addAttribute("rpgs", listRpg);
		return "dashboard";
	}

	@GetMapping(Routes.TEST)
	public String showTest(Model model) {

		model.addAttribute("markdown", MarkdownParsingService.parse("This is **SPARTA**"));
		return "test";
	}
}
