package com.verizon.zoetool.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.ui.ModelMap;

@Controller
public class LoginController{
 
	@RequestMapping(value="/login.do", method = RequestMethod.GET)
	public String login(ModelMap model) {
		return "login"; 
	}
 
	@RequestMapping(value="/logout.do", method = RequestMethod.GET)
	public String logout(ModelMap model) {
		return "login";
	}
	
	@RequestMapping(value="/invalidSession.htm", method = RequestMethod.GET)
	public String invalidsessionerror(ModelMap model) {
		model.addAttribute("error", "invalid session");
		return "login";
	}
}
