package com.example.demo.validators;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.example.demo.models.User;

@Component
public class UserUpdateValidator implements Validator{
	
	@Override
	public boolean supports(Class<?> aClass) {
		return User.class.equals(aClass);
	}

	@Override
	public void validate(Object o, Errors errors) {
		
		User user = (User) o;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "username", "username.empty", "You must enter a username!");
        if (user.getUsername().length() < 4 || user.getUsername().length() > 32) {
        	System.out.println("Username error");
            errors.rejectValue("username", "username.size", "The size must be between 4 and 32!");
        }
        
        if(!user.getPassword().isEmpty() || !user.getPasswordConfirm().isEmpty())
        {
        	ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "password.empty", "Password can't be empty!");

        	if (user.getPassword().length() < 8 || user.getPassword().length() > 32) {
            	errors.rejectValue("password", "password.length", "The size must be between 6 and 32!");
            }

            if (!user.getPasswordConfirm().equals(user.getPassword())) {
            	System.out.println("Username not equal error");
            	errors.rejectValue("passwordConfirm", "passwordConfirm.value", "This password doesn't match!");
            }
        }
        
	}
}
