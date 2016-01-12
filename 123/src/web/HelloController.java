package com.verizon.zoetool.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.security.Principal;
import java.util.Date;

@Controller
@RequestMapping("/hello.htm")
public class HelloController {
	
	@RequestMapping(method = RequestMethod.GET)
	public String printWelcome(ModelMap model, Principal principal)
	{
		String now = (new Date()).toString();
		String user = principal.getName();
		model.addAttribute("now", now);
		model.addAttribute("username", user);
		return "hello";
	}
	
}
