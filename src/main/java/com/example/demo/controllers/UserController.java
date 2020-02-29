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
import com.example.demo.service.SecurityService;
import com.example.demo.service.UserService;
import com.example.demo.validator.UserValidator;

@Controller
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
    private SecurityService securityService;

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
    	
    	System.out.println(userForm.toString());
    	
        userValidator.validate(userForm, bindingResult);

        if (bindingResult.hasErrors()) {
        	System.out.println("RETURN WITH ERROR");
            return "signup";
        }

        userService.save(userForm);

        securityService.autoLogin(userForm.getEmail(), userForm.getPasswordConfirm());

        return "redirect:/dashboard";
    }

    @GetMapping("/signin")
    public String signin(Model model, String error, String logout) {
    	System.out.println("ADDED USER");
        if (error != null)
        	System.out.println("Your email and/or password is invalid.");
            model.addAttribute("error", "Your email and/or password is invalid.");

        if (logout != null)
        	System.out.println("You have been logged out successfully.");
            model.addAttribute("message", "You have been logged out successfully.");

        return "signin";
    }
    
    @GetMapping(value="/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null){

            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return "redirect:/dashboard"; //You can redirect wherever you want, but generally it's a good practice to show login screen again.
    }
}
