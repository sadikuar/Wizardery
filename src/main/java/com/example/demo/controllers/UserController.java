package com.example.demo.controllers;

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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.models.User;
import com.example.demo.services.SecurityServiceInterface;
import com.example.demo.services.UserServiceInterface;
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

	@GetMapping(Routes.PROFILE)
	public String showProfile() {
		return "profile";
	}

	@GetMapping(Routes.SIGNUP)
	public String signup(Model model) {
		model.addAttribute("userForm", new User());

		return "signup";
	}

	@PostMapping(Routes.SIGNUP)
	public String signup(@ModelAttribute("userForm") User userForm, BindingResult bindingResult) {

		userSignupValidator.validate(userForm, bindingResult);

		if (bindingResult.hasErrors()) {
			return "signup";
		}

		userService.save(userForm);

		securityService.autoLogin(userForm.getEmail(), userForm.getPasswordConfirm());

		return "redirect:/signin/confirm";
	}

	@GetMapping(Routes.SIGNIN)
	public String signin(Model model, String error, String logout) {
		if (error != null) {
			System.out.println("error");
			model.addAttribute("error", "Your email and/or password is invalid.");
		}
		if (logout != null) {
			System.out.println("logout");
			model.addAttribute("logout", "You have been logged out successfully.");
		}

		return "signin";
	}

	@GetMapping(Routes.SIGNIN_CONFIRM)
	public String signinConfirm(HttpSession session) {
		if (SecurityContextHolder.getContext().getAuthentication() != null
				&& SecurityContextHolder.getContext().getAuthentication().isAuthenticated()
				&& !(SecurityContextHolder.getContext().getAuthentication() instanceof AnonymousAuthenticationToken)) {
			String email = SecurityContextHolder.getContext().getAuthentication().getName();
			User user = userService.findByEmail(email);
			session.setAttribute("username", user.getUsername());
		}
		return "redirect:/dashboard";
	}

	@GetMapping(Routes.PROFILE_UPDATE)
	public String update(Model model) {

		if (SecurityContextHolder.getContext().getAuthentication() != null
				&& SecurityContextHolder.getContext().getAuthentication().isAuthenticated()
				&& !(SecurityContextHolder.getContext().getAuthentication() instanceof AnonymousAuthenticationToken)) {
			String email = SecurityContextHolder.getContext().getAuthentication().getName();
			User user = userService.findByEmail(email);
			model.addAttribute("user", user);
		}

		return "profile-update";
	}

	@PostMapping(Routes.PROFILE_UPDATE)
	public String update(@ModelAttribute User user, BindingResult bindingResult) {
		userUpdateValidator.validate(user, bindingResult);

		if (bindingResult.hasErrors()) {
			return "profile-update";
		}

		userService.save(user);

		return "redirect:/dashboard";
	}
	
	@Transactional
	@PostMapping(Routes.PROFILE_DELETE)
	public String delete(HttpServletRequest request) throws ServletException
	{
		String email = SecurityContextHolder.getContext().getAuthentication().getName();
		userService.deleteByEmail(email);
		
		request.logout();
		return "redirect:/dashboard";
	}
}
