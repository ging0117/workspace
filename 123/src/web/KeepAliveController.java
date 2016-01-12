package com.verizon.zoetool.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class KeepAliveController {
	
	@RequestMapping(value="/KeepAlive.do", method = RequestMethod.GET)
	public String alive(ModelMap model) {
		return "KeepAlive"; 
	}
	
	@RequestMapping(value="/KeepAlive.do", method = RequestMethod.HEAD)
	public String alive2(ModelMap model) {
		return "KeepAlive";
	}
}
