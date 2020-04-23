package com.example.demo.controllers;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.demo.utils.Routes;

@Controller
public class WizarderyErrorController implements ErrorController {

	@GetMapping(Routes.ERROR)
	public String handleError(HttpServletRequest httpServletRequest) {
		Object status = httpServletRequest.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);

		if (status != null) {
			Integer statusCode = Integer.valueOf(status.toString());

			if (statusCode == HttpStatus.NOT_FOUND.value()) {
				return "error-404";
			}

			if (statusCode == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
				return "error-500";
			}
		}
		return "error";
	}

	@GetMapping(Routes.ERROR_FORBIDDEN)
	public String handleForbiddenError() {
		return "error-403";
	}

	@Override
	public String getErrorPath() {
		return Routes.ERROR;
	}

}
