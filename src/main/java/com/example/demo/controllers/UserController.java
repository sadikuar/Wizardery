package com.example.demo.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.models.User;
import com.example.demo.service.SecurityService_I;
import com.example.demo.service.UserService_I;
import com.example.demo.validator.UserValidator;

@Controller
public class UserController {
	
	@Autowired
	private UserService_I userService;
	
	@Autowired
    private SecurityService_I securityService;

    @Autowired
    private UserValidator userValidator;
    
	@GetMapping("/profile")
	private String showProfile() {
		return "profile";
	}
	
	@GetMapping("/signup")
    public String signup(Model model) {
        model.addAttribute("userForm", new User());

        return "signup";
    }

    @PostMapping("/signup")
    public String signup(@ModelAttribute("userForm") User userForm, BindingResult bindingResult) {
    	
        userValidator.validate(userForm, bindingResult);

        if (bindingResult.hasErrors()) {
            return "signup";
        }

        userService.save(userForm);

        securityService.autoLogin(userForm.getEmail(), userForm.getPasswordConfirm());

        return "redirect:/dashboard";
    }

    @GetMapping("/signin")
    public String signin(Model model, String error, String logout) {
        if (error != null) {
        	model.addAttribute("error", "Your email and/or password is invalid.");
        }
        if (logout != null) {
        	model.addAttribute("logout", "You have been logged out successfully.");
        }

        return "signin";
    }
    
    @GetMapping(value="/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null){
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return "redirect:/signin?logout";
    }
}
