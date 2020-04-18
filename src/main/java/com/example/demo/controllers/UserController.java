package com.example.demo.controllers;

import java.io.IOException;
import java.nio.file.Files;
import java.security.Principal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.models.Rpg;
import com.example.demo.models.Scenario;
import com.example.demo.models.User;
import com.example.demo.repositories.RpgRepository;
import com.example.demo.repositories.ScenarioRepository;
import com.example.demo.repositories.UserRepository;
import com.example.demo.services.MarkdownParsingService;
import com.example.demo.services.SecurityServiceInterface;
import com.example.demo.services.StorageService;
import com.example.demo.services.UserServiceInterface;
import com.example.demo.utils.Directory;
import com.example.demo.utils.Routes;
import com.example.demo.validators.UserSignupValidator;
import com.example.demo.validators.UserUpdateValidator;

@Controller
public class UserController {

	@Autowired
	private UserServiceInterface userService;

	@Autowired
	private SecurityServiceInterface securityService;

	@Autowired
	private UserSignupValidator userSignupValidator;

	@Autowired
	private UserUpdateValidator userUpdateValidator;

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private RpgRepository rpgRepository;
	
	@Autowired
	private ScenarioRepository scenarioRepository;
	
	@GetMapping(Routes.ADMIN)
	public String showAdminPage(Model model) {
		List<User> listUser = userRepository.findAll();
		List<Rpg> listRpg = rpgRepository.findAll();
		List<Scenario> listScenario = scenarioRepository.findAll();
		
		model.addAttribute("users", listUser);
		model.addAttribute("rpgs", listRpg);
		model.addAttribute("scenarios", listScenario);
		
		model.addAttribute("rpg", new Rpg());
		model.addAttribute("scenario", new Scenario());
		
		return "wizardery-admin";
	}

	@GetMapping(Routes.USER_DETAILS + "{id}")
	public String showProfile(Model model, @PathVariable Long id) {
		Optional<User> optionalUser = userRepository.findById(id);

		if (optionalUser.isPresent()) {
			User user = optionalUser.get();
			model.addAttribute("user", user);

			if (!user.getImageUrl().isEmpty()) {
				String[] tabFile = user.getImageUrl().split("\\.");
				model.addAttribute("imageName", tabFile[0]);
				model.addAttribute("imageExt", tabFile[1]);
			}

			if (!user.getFavoriteRpgs().isEmpty()) {
				user.getFavoriteRpgs().forEach(MarkdownParsingService::parse);

				model.addAttribute("rpgs", user.getFavoriteRpgs());
			}

			if (!user.getFavoriteScenarios().isEmpty()) {
				user.getFavoriteScenarios().forEach(MarkdownParsingService::parse);

				model.addAttribute("scenarios", user.getFavoriteScenarios());
			}

			return "user-details";
		}

		return "dashboard";
	}

	@GetMapping(Routes.SIGNUP)
	public String signup(Model model) {
		model.addAttribute("user", new User());

		return "signup";
	}

	@PostMapping(Routes.SIGNUP)
	public String signup(HttpSession session, @ModelAttribute("user") User user, BindingResult bindingResult) {

		userSignupValidator.validate(user, bindingResult);

		if (bindingResult.hasErrors()) {
			return "signup";
		}

		MultipartFile multipartFile = user.getUploadedFile();
		if (multipartFile != null && !multipartFile.getOriginalFilename().isBlank()) {
			String filePath = StorageService.saveToDisk(multipartFile, Directory.PROFILE_DIR);
			String[] tab = filePath.split("/");

			session.setAttribute("imageName", tab[0]);
			session.setAttribute("imageExt", tab[1]);

			user.setImageUrl(tab[tab.length - 1]);
		}
		else
		{
			user.setImageUrl("");
		}

		userService.save(user);

		securityService.autoLogin(user.getEmail(), user.getPasswordConfirm());

		return "redirect:/signin/confirm";
	}

	@GetMapping(Routes.SIGNIN)
	public String signin(Model model, String error, String logout, HttpSession session) {
		if (error != null) {
			model.addAttribute("error", "Your email and/or password is invalid.");
		}

		if (logout != null) {
			model.addAttribute("logout", "You have been logged out successfully.");
			session.setAttribute("username", "");
			session.setAttribute("userId", "");
			session.setAttribute("profileImg", "");
			session.setAttribute("imageName", "");
			session.setAttribute("imageExt", "");
			session.setAttribute("userRole", "");
		}

		return "signin";
	}

	@GetMapping(Routes.SIGNIN_CONFIRM)
	public String signinConfirm(HttpSession session, Principal principal) {

		if (principal != null) {
			String email = principal.getName();
			User user = userService.findByEmail(email);
			session.setAttribute("username", user.getUsername());
			session.setAttribute("userId", user.getId());
			session.setAttribute("userRole", user.getRole().getName());
			if (!user.getImageUrl().isEmpty()) {
				String[] tabFile = user.getImageUrl().split("\\.");
				session.setAttribute("imageName", tabFile[0]);
				session.setAttribute("imageExt", tabFile[1]);
				
				System.out.println(tabFile[0] + "/" + tabFile[1]);
				session.setAttribute("profile_img", tabFile[0] + tabFile[1]);
			} else {
				session.setAttribute("imageName", "");
				session.setAttribute("imageExt", "");
				session.setAttribute("profile_img", "");
			}
		}
		return "redirect:/dashboard";
	}

	@GetMapping(Routes.USER_UPDATE)
	public String showUserUpdate(Model model, HttpSession session) {

		if (SecurityContextHolder.getContext().getAuthentication() != null
				&& SecurityContextHolder.getContext().getAuthentication().isAuthenticated()
				&& !(SecurityContextHolder.getContext().getAuthentication() instanceof AnonymousAuthenticationToken)) {
			String email = SecurityContextHolder.getContext().getAuthentication().getName();
			User user = userService.findByEmail(email);
			session.setAttribute("userId", user.getId());
			model.addAttribute("user", user);
		}

		return "user-update";
	}

	@PostMapping(Routes.USER_DETAILS + "{id}" + "/update")
	public String updateUser(@ModelAttribute User user, BindingResult bindingResult, HttpSession session,
			@PathVariable Long id) {
		userUpdateValidator.validate(user, bindingResult);
		Optional<User> oldUser = userRepository.findById(id);
		if (bindingResult.hasErrors()) {
			System.out.println(bindingResult.getAllErrors());
			return "user-update";
		}

		MultipartFile multipartFile = user.getUploadedFile();
		if (multipartFile != null && !multipartFile.getOriginalFilename().isBlank()) {
			String filePath = StorageService.saveToDisk(multipartFile, Directory.PROFILE_DIR);
			String[] tab = filePath.split("/");
			
			String imgName = tab[tab.length - 1];
			String[] image = imgName.split("\\.");

			session.setAttribute("imageName", image[0]);
			session.setAttribute("imageExt", image[1]);
			session.setAttribute("profile_img", tab[0] + tab[1]);

			user.setImageUrl(tab[tab.length - 1]);
		} else if (oldUser.isPresent()) {
			user.setImageUrl(oldUser.get().getImageUrl());
		} else {
			user.setImageUrl("");
		}

		userService.update(user);
		session.setAttribute("username", user.getUsername());

		return "redirect:/dashboard";
	}

	@GetMapping("/profile/image/{fileName}/{fileExt}")
	@ResponseBody
	public byte[] getImage(@PathVariable String fileName, @PathVariable String fileExt) {
		fileName = Directory.PROFILE_DIR + fileName + "." + fileExt;
		java.io.File file = new java.io.File(fileName);
		try {
			return Files.readAllBytes(file.toPath());
		} catch (IOException e) {
			return null;
		}
	}

	@Transactional
	@PostMapping(Routes.USER_DETAILS + "{id}" + "/delete")
	public String delete(HttpServletRequest request) throws ServletException {
		String email = SecurityContextHolder.getContext().getAuthentication().getName();
		userService.deleteByEmail(email);

		request.logout();
		return "redirect:/dashboard";
	}
}
