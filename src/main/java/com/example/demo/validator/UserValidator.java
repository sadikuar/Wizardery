package com.example.demo.validator;

import org.hibernate.validator.internal.constraintvalidators.bv.EmailValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.example.demo.models.User;
import com.example.demo.service.UserService;

@Component
public class UserValidator implements Validator{

	@Autowired
    private UserService userService;
	
	@Override
	public boolean supports(Class<?> aClass) {
		return User.class.equals(aClass);
	}

	@Override
	public void validate(Object o, Errors errors) {
		
		System.out.println("VALIDATING");
		User user = (User) o;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "username", "username.empty", "You must enter a username!");
        if (user.getUsername().length() < 4 || user.getUsername().length() > 32) {
        	System.out.println("USERNAME");
            errors.rejectValue("username", "username.size", "The size must be between 4 and 32!");
        }
        
        if(!isValidEmail(user.getEmail())) {
        	System.out.println("EMAIL");
        	errors.rejectValue("email", "email.structure", "This is not a valid email!");
        }
        
        if (userService.findByEmail(user.getEmail()) != null) {
        	System.out.println("EXISTS");
        	errors.rejectValue("email", "email.dupplicate", "This email is already used!");
        }

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "NotEmpty");
        if (user.getPassword().length() < 8 || user.getPassword().length() > 32) {
        	System.out.println("PASSWORD SIZE");
        	errors.rejectValue("password", "password.length", "The size must be between 6 and 32!");
        }

        if (!user.getPasswordConfirm().equals(user.getPassword())) {
        	System.out.println("PASSWORD BAD");
        	errors.rejectValue("passwordConfirm", "passwordConfirm.value", "This password doesn't match!");
        }
        
        
		
	}
	
	private boolean isValidEmail(String email)
	{
		String regex = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
	    return email.matches(regex);
	}

}
