package com.example.demo.validators;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.example.demo.models.Scenario;

@Component
public class ScenarioValidator implements Validator {

	@Override
	public boolean supports(Class<?> aClass) {
		return Scenario.class.equals(aClass);
	}

	@Override
	public void validate(Object target, Errors errors) {
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "name.empty", "You must enter a name!");
	}

}
