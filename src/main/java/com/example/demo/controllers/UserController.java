package com.example.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.models.User;
import com.example.demo.service.SecurityService_I;
import com.example.demo.service.UserService_I;
import com.example.demo.validator.UserSignupValidator;

@Controller
public class UserController {
	
	@Autowired
	private UserService_I userService;
	
	@Autowired
    private SecurityService_I securityService;

    @Autowired
    private UserSignupValidator userSignupValidator;
    
	@GetMapping("/user/profile")
	private String showProfile() {
		return "profile";
	}
	
	@GetMapping("/user/signup")
    public String signup(Model model) {
        model.addAttribute("userForm", new User());

        return "signup";
    }

    @PostMapping("/user/signup")
    public String signup(@ModelAttribute("userForm") User userForm, BindingResult bindingResult) {
    	
    	userSignupValidator.validate(userForm, bindingResult);

        if (bindingResult.hasErrors()) {
            return "signup";
        }

        userService.save(userForm);

        securityService.autoLogin(userForm.getEmail(), userForm.getPasswordConfirm());

        return "redirect:/dashboard";
    }

    @GetMapping("/user/signin")
    public String signin(Model model, String error, String logout) {	
        if (error != null) {
        	model.addAttribute("error", "Your email and/or password is invalid.");
        }
        if (logout != null) {
        	model.addAttribute("logout", "You have been logged out successfully.");
        }

        return "signin";
    }
}
