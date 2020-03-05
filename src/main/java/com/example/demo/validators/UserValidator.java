package com.example.demo.validators;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.example.demo.models.User;
import com.example.demo.services.UserServiceInterface;

@Component
public class UserValidator implements Validator {

	@Autowired
	private UserServiceInterface userService;

	@Override
	public boolean supports(Class<?> aClass) {
		return User.class.equals(aClass);
	}

	@Override
	public void validate(Object o, Errors errors) {

		User user = (User) o;

		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "username", "username.empty", "You must enter a username!");
		if (user.getUsername().length() < 4 || user.getUsername().length() > 32) {
			errors.rejectValue("username", "username.size", "The size must be between 4 and 32!");
		}

		if (!isValidEmail(user.getEmail())) {
			errors.rejectValue("email", "email.structure", "This is not a valid email!");
		}

		if (userService.findByEmail(user.getEmail()) != null) {
			errors.rejectValue("email", "email.dupplicate", "This email is already used!");
		}

		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "NotEmpty");
		if (user.getPassword().length() < 8 || user.getPassword().length() > 32) {
			errors.rejectValue("password", "password.length", "The size must be between 6 and 32!");
		}

		if (!user.getPasswordConfirm().equals(user.getPassword())) {
			errors.rejectValue("passwordConfirm", "passwordConfirm.value", "This password doesn't match!");
		}

	}

	private boolean isValidEmail(String email) {
		String regex = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
		return email.matches(regex);
	}

}
