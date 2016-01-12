package com.verizon.zoetool.web;

import java.security.Principal;
import java.util.LinkedHashMap;
import java.util.Map;
import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.verizon.zoetool.domain.SimpleCustomer;
import com.verizon.zoetool.service.CheckDownstreamServiceManager;
import com.verizon.zoetool.utils.AppUtils;

@Controller
@SessionAttributes
public class CheckDownstreamServiceController{
	private static final Logger logger = Logger.getLogger(CheckDownstreamServiceController.class);
	
	@RequestMapping(value = "/checkdownstreamservice2.htm", method = RequestMethod.POST)
	public String cancelSubscription(
			@Valid @ModelAttribute("customer") SimpleCustomer customer, 
			BindingResult result,
			ModelMap model,
			Principal principal)
	{
		String user = principal.getName();		
		
		if (result.hasErrors())
		{
			return "checkdownstreamservice";
		}
		
		logger.info("[user=" + user + "]\t[view=checkdownstreamservice2]\t[env=" + customer.getEnv() + "]\t[pcn=" + customer.getPcn() + "]");
		
		AppUtils.Env env = AppUtils.getEnv(customer.getEnv());
		CheckDownstreamServiceManager m = new CheckDownstreamServiceManager(user);		
		m.check(env, customer.getPcn());
		
		model.addAttribute("customer", customer);
		model.addAttribute("rb", m.sRB);
		model.addAttribute("scm", m.sSCM);
		model.addAttribute("et", m.sET);
		model.addAttribute("pluck", m.sPluck);
		model.addAttribute("ds", m.sDS);
		
		return "checkdownstreamservice2";
	}
	@RequestMapping(value = "/checkdownstreamservice.htm", method = RequestMethod.GET)
	public String showForm(ModelMap model, Principal principal)
	{
		String user = principal.getName();
		logger.info("[user=" + user + "]\t[view=checkdownstreamservice]");
		
		return "checkdownstreamservice";
	}
	
	@ModelAttribute("customer")
	public SimpleCustomer getCustomerObject()
	{
		return new SimpleCustomer();
	}
	
	@ModelAttribute("envList")
	public static Map<String, String> populateEnvList()
	{
		Map<String,String> env = new LinkedHashMap<String,String>();
		
		for(String e: AppUtils.getAllAvailableEnvs())
		{
			if (e.equalsIgnoreCase("dyn"))
				env.put("dyn", "dyn");
			if (e.equalsIgnoreCase("sit"))
				env.put("sit", "sit");
			if (e.equalsIgnoreCase("stg"))
				env.put("stg", "stg");
			if (e.equalsIgnoreCase("prd"))
				env.put("prd", "prd");
		}
		
		return env;
	}
}
