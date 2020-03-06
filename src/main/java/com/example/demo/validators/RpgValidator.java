package com.example.demo.validators;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.example.demo.models.Rpg;

@Component
public class RpgValidator implements Validator{

	@Override
	public boolean supports(Class<?> aClass) {
		return Rpg.class.equals(aClass);
	}

	@Override
	public void validate(Object o, Errors errors) {
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "name.empty", "You must enter a name!");
		
	}

}
