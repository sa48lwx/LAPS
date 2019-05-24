package com.example.portal.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class CredentialsController {

	@GetMapping("/home/createCredentials")
	public ModelAndView createCredentials() {
		ModelAndView mav = new ModelAndView("createCredentials");
		return mav;
	}
}
